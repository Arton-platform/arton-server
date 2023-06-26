package com.arton.backend.review.application.port.in;

import com.arton.backend.review.application.data.ReviewCreateDto;

public interface ReviewRegistUseCase {
    void regist(long userId, ReviewCreateDto reviewCreateDto);
}
