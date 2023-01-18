package com.arton.backend.infra.file;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 이미지 업로드 유틸
 */
@Slf4j
public class FileUploadUtils {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) {
        Path uploadPath = Paths.get(System.getProperty("user.dir") + uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                log.error("Could createDirectories: " + fileName, e);
            }
        }

        try (InputStream iStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(iStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            log.error("Could not save file: " + fileName, e);
        }
    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(System.getProperty("user.dir")+dir);

        try {
            Files.list(dirPath).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException ex) {
                        log.error("Could not delete file: " + file, ex);
                    }
                }
            });
        } catch (IOException e) {
            log.error("Could not list directory: ", e);
        }
    }

    /**
     * / + 폴더명 형식으로 지정한다.
     * @param directory
     * @return
     */
    public static List<String> getFileNameInDirectory(String directory) {
        Path termsPath = Paths.get(System.getProperty("user.dir") + directory);
        log.info("path {}", termsPath.getFileName());
        if (!Files.exists(termsPath)) {
            try {
                Files.createDirectories(termsPath);
            } catch (IOException e) {
                log.error("Could createDirectories: {}", e);
            }
        }

        Stream<Path> stream = null;
        try {
            stream = Files.list(termsPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INVALID_URI_REQUEST.getMessage(), ErrorCode.INVALID_URI_REQUEST);
        }
        return Optional.ofNullable(stream).orElseThrow(()->new CustomException(ErrorCode.INVALID_URI_REQUEST.getMessage(), ErrorCode.INVALID_URI_REQUEST)).filter(file -> !Files.isDirectory(file))
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
