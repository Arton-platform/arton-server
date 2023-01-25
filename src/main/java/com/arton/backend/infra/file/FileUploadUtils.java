package com.arton.backend.infra.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadUtils {
    void delete(Long userId, String dirName);
    String upload(MultipartFile multipartFile, String dirName);
    String getDefaultImageUrl();
    List<String> getFileNameInDirectory(String directory);
}
