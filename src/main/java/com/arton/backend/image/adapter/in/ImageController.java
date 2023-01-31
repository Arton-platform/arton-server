package com.arton.backend.image.adapter.in;

import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileUploadUtils fileUploadUtils;

    @PostMapping(value = "/test/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse> uploadTest(@RequestPart(name = "images") List<MultipartFile> multipartFileList) {
        fileUploadUtils.uploadMultipleFiles(multipartFileList, "arton/image/performances/1");
        return ResponseEntity.ok(CommonResponse.builder().message("업로드 성공").status(HttpStatus.OK.value()).build());
    }
}
