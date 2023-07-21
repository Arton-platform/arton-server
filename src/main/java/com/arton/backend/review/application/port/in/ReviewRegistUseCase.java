package com.arton.backend.review.application.port.in;

import com.arton.backend.review.application.data.ReviewCreateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewRegistUseCase {
    /**
     * 이미지도 추가 가능해야함.
     * @param userId
     * @param reviewCreateDto
     */
    Long regist(long userId, ReviewCreateDto reviewCreateDto, List<MultipartFile> multipartFileList);
}
