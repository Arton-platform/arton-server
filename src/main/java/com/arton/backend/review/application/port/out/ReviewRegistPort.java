package com.arton.backend.review.application.port.out;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.review.adapter.out.persistence.ReviewEntity;

public interface ReviewRegistPort {
    void regist(ReviewEntity<CommonResponse> entity);
}
