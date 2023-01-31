package com.arton.backend.infra.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 이미지 업로드 유틸 S3
 * s3에 올리려면 프로파일 추가하면 됨.
 */
@Slf4j
@Profile(value = {"aws", "local"})
@Service
@RequiredArgsConstructor
public class FileUploadS3 implements FileUploadUtils {
    @Value("${spring.bucket}")
    private String bucket;
    @Value("${spring.full-bucket}")
    private String prefix;
    @Value("${spring.default-image}")
    private String defaultImageUrl;
    // s3 uploader
    private final AmazonS3Client amazonS3Client;

    @Override
    public String getDefaultImageUrl() {
        return defaultImageUrl;
    }

    /**
     * html 읽어서 raw value로 전환.
     * @param directory
     * @return
     */
    @Override
    public String getFileContent(String directory) {
        S3Object object = amazonS3Client.getObject(new GetObjectRequest(bucket, directory));
        S3ObjectInputStream objectContent = object.getObjectContent();
        try {
            byte[] bytes = IOUtils.toByteArray(objectContent);
            String response = new String(bytes, StandardCharsets.UTF_8);
            return response;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_URI_REQUEST.getMessage(), ErrorCode.INVALID_URI_REQUEST);
        }
    }


    /**
     * arton/terms 로 넘어옴
     * @param directory
     * @return
     */
    @Override
    public List<String> getFileNameInDirectory(String directory) {
        List<String> fileNames = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucket);
        if (!directory.equals("")) {
            listObjectsRequest.setPrefix(directory + "/");
        }
        listObjectsRequest.setDelimiter("/");
        ObjectListing s3Objects;
        do {
            s3Objects = amazonS3Client.listObjects(listObjectsRequest);
            // 파일 조회
            for (S3ObjectSummary objectSummary : s3Objects.getObjectSummaries()) { // prefix 경로의 파일명이 있다면 조회
                String key = objectSummary.getKey();
                String[] split = key.split(directory + "/");
                if (split.length >= 2) {
                    fileNames.add(prefix + key);
                }
            }
            listObjectsRequest.setMarker(s3Objects.getNextMarker()); // listObjects 1,000건만 조회
        } while (s3Objects.isTruncated());
        return fileNames;
    }

    /**
     * s3 file upload
     * @param multipartFile
     * @param dirName
     * @return
     */
    @Override
    public String upload(MultipartFile multipartFile, String dirName) {
        validateFile(multipartFile);
        String storeFileName = dirName + "/" + createStoreFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try(InputStream inputStream = multipartFile.getInputStream()){
            amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED.getMessage(), ErrorCode.FILE_UPLOAD_FAILED);
        }
        return amazonS3Client.getUrl(bucket, storeFileName).toString();
    }

    @Override
    public List<String> uploadMultipleFiles(List<MultipartFile> multipartFiles, String dirName) {
        return null;
    }

    /**
     * s3 file delete
     * @param dirName
     * @return
     */
    @Override
    public void delete(Long userId, String dirName) {
        // 기본 이미지가 아니라면 삭제 진행
        if (!dirName.equals(defaultImageUrl)) {
            amazonS3Client.deleteObject(bucket, dirName.substring(prefix.length()));
        }
    }

    private void validateFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_EMPTY.getMessage(), ErrorCode.FILE_EMPTY);
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
