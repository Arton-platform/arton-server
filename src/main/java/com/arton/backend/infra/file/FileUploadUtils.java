package com.arton.backend.infra.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 파일 업로드, 다운로드와 관련된 유틸.
 */
public interface FileUploadUtils {
    void deleteFile(Long userId, String dirName);
    void deleteFiles(Long id, List<String> dirNames);
    void copyFile(Long id, String dirName);
    String upload(MultipartFile multipartFile, String dirName);
    List<String> uploadMultipleFiles(List<MultipartFile> multipartFiles, String dirName);
    String getDefaultImageUrl();
    String getFileContent(String directory);
    List<String> getFileNameInDirectory(String directory);
    MultipartFile fileToMultipartFile(String url);
}
