package com.arton.backend.infra.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 파일 업로드, 다운로드와 관련된 유틸.
 */
public interface FileUploadUtils {
    void delete(Long userId, String dirName);
    String upload(MultipartFile multipartFile, String dirName);
    String getDefaultImageUrl();
    String getFileContent(String directory);
    List<String> getFileNameInDirectory(String directory);
}
