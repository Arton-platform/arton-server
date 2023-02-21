package com.arton.backend.infra.file;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 이미지 업로드 유틸 로컬
 */
@Slf4j
@Profile(value = {"test"})
@Service
@RequiredArgsConstructor
public class FileUploadLocal implements FileUploadUtils{
    @Value("${spring.default-image-local}")
    private String defaultImageUrl;
    @Value("${spring.user.image.dir}")
    private String rootDir;
    @Value("${spring.performance.image.dir}")
    private String rootPerformanceDir;

    @Override
    public void deleteFile(Long userId, String dir) {
        // 기본 이미지가 아니라면 삭제 진행
        if (!dir.equals(defaultImageUrl)) {
            Path dirPath = Paths.get(System.getProperty("user.dir") + rootDir + userId);

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
    }

    /**
     * 공연 제거용 메소드
     * @param id
     * @param dirNames
     */
    @Override
    public void deleteFiles(Long id, List<String> dirNames) {
        Path dirPath = Paths.get(System.getProperty("user.dir") + rootPerformanceDir + id);

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

    @Override
    public String upload(MultipartFile multipartFile, String dirName) {
        validateFile(multipartFile);
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = createStoreFileName(originalFilename);
        Path uploadPath = Paths.get(System.getProperty("user.dir")  + dirName);
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
        return fileName;
    }

    @Override
    public List<String> uploadMultipleFiles(List<MultipartFile> multipartFiles, String dirName) {
        return null;
    }

    @Override
    public String getDefaultImageUrl() {
        return defaultImageUrl;
    }

    @Override
    public String getFileContent(String directory) {
        return "지원하지 않는 기능입니다...";
    }

    /**
     * / + 폴더명 형식으로 지정한다.
     * @param directory
     * @return
     */
    @Override
    public List<String> getFileNameInDirectory(String directory) {
        Path termsPath = Paths.get(System.getProperty("user.dir") + "/" + directory);
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

    private void validateFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_EMPTY.getMessage(), ErrorCode.FILE_EMPTY);
        }
        if (!multipartFile.getContentType().toLowerCase(Locale.ROOT).contains("image")) {
            throw new CustomException(ErrorCode.UNSUPPORTED_MEDIA_ERROR.getMessage(), ErrorCode.UNSUPPORTED_MEDIA_ERROR);
        }
    }

    private String extractExt(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1); // 확장자
    }

    private String createStoreFileName(String filename) {
        String ext = extractExt(filename);
        MessageDigest mdMD5;
        try {
            mdMD5 = MessageDigest.getInstance("MD5");
            mdMD5.update(filename.getBytes("UTF-8"));
            byte[] md5Hash = mdMD5.digest();
            StringBuilder hexMD5hash = new StringBuilder();
            for (byte b : md5Hash) {
                String hexString = String.format("%02x", b);
                hexMD5hash.append(hexString);
            }
            return hexMD5hash.toString()+"."+ext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "default.png";
    }
}
