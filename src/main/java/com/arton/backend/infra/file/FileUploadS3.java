package com.arton.backend.infra.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 이미지 업로드 유틸 S3
 * s3에 올리려면 프로파일 추가하면 됨.
 */
@Slf4j
@Profile(value = {"aws", "local", "dev"})
@Service
@RequiredArgsConstructor

public class FileUploadS3 implements FileUploadUtils {
    @Value("${spring.bucket}")
    private String bucket;
    @Value("${spring.full-bucket}")
    private String prefix;
    @Value("${spring.default-image}")
    private String defaultImageUrl;
    @Value("${spring.performance.image.dir}")
    private String defaultPerformanceUrl;
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
            byte[] bytes = IOUtils.toByteArray(inputStream);
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, byteArrayInputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED.getMessage(), ErrorCode.FILE_UPLOAD_FAILED);
        }
        return amazonS3Client.getUrl(bucket, storeFileName).toString();
    }

    @Override
    public List<String> uploadMultipleFiles(List<MultipartFile> multipartFiles, String dirName) {
        List<String> result = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            // 검증
            validateFile(multipartFile);
            String storeFileName = dirName + "/" + createStoreFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            try(InputStream inputStream = multipartFile.getInputStream()){
                byte[] bytes = IOUtils.toByteArray(inputStream);
                objectMetadata.setContentLength(bytes.length);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, byteArrayInputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED.getMessage(), ErrorCode.FILE_UPLOAD_FAILED);
            }
            result.add(amazonS3Client.getUrl(bucket, storeFileName).toString());
        }
        return result;
    }

    /**
     * s3 file delete
     * @param dirName
     * @return
     */
    @Override
    public void deleteFile(Long userId, String dirName) {
        // 기본 이미지가 아니라면 삭제 진행
        if (!dirName.equals(defaultImageUrl)) {
            try{
                amazonS3Client.deleteObject(bucket, dirName.substring(prefix.length()));
            }catch (Exception e) {
                throw new CustomException(ErrorCode.DELETE_ERROR.getMessage(), ErrorCode.DELETE_ERROR);
            }
        }
    }

    /**
     * 기존 deleteFile 이용.
     * @param id
     * @param dirNames
     */
    @Override
    public void deleteFiles(Long id, List<String> dirNames) {
        for (String dirName : dirNames) {
            deleteFile(id, dirName);
        }
    }

    @Override
    public String copyFile(Long id, String dirName) {
        try {
            String origin = dirName.substring(prefix.length());
            String temp = origin.substring(defaultPerformanceUrl.length());
            int lastIdx = temp.lastIndexOf("/");
            String copy = defaultPerformanceUrl + id + temp.substring(lastIdx);
            CopyObjectRequest copyObjRequest = new CopyObjectRequest(
                    this.bucket,
                    origin,
                    this.bucket,
                    copy
            ).withCannedAccessControlList(CannedAccessControlList.PublicRead);
            //Copy
            amazonS3Client.copyObject(copyObjRequest);
            return prefix + copy;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FILE_COPY_FAILED.getMessage(), ErrorCode.FILE_COPY_FAILED);
        }
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

    @Override
    public MultipartFile fileToMultipartFile(String url) {
        File file = new File(url);
        FileItem fileItem = null;
        try {
            fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_EMPTY.getMessage(), ErrorCode.FILE_EMPTY);
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new CustomException(ErrorCode.FILE_EMPTY.getMessage(), ErrorCode.FILE_EMPTY);
        }
        OutputStream outputStream = null;
        try {
            outputStream = fileItem.getOutputStream();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IO_EXCEPTION.getMessage(), ErrorCode.IO_EXCEPTION);
        }
        try {
            org.apache.commons.io.IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IO_EXCEPTION.getMessage(), ErrorCode.IO_EXCEPTION);
        }
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }
}
