package com.arton.backend.review.application.port.in;

import com.arton.backend.review.application.data.ReviewEditDto;

public interface ReviewEditUseCase {
    void edit(long userId, ReviewEditDto reviewEditDto);
}
