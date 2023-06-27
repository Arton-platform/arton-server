package com.arton.backend.reviewhit.application.port.out;

import com.arton.backend.reviewhit.domain.ReviewHit;

public interface ReviewHitSavePort {
    ReviewHit save(ReviewHit reviewHit);
}
