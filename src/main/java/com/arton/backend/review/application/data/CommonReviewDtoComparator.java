package com.arton.backend.review.application.data;

import java.util.Comparator;

public class CommonReviewDtoComparator implements Comparator<CommonReviewDto> {
    @Override
    public int compare(CommonReviewDto f1, CommonReviewDto f2) {
        if (f1.getParentId() == null) {
            return 1;
        } else if (f2.getParentId() == null) {
            return 1;
        } else if (f1.getParentId() != null && f2.getParentId() != null) {
            if (f1.getParentId() > f2.getParentId()) {
                return 1;
            } else if (f1.getParentId() < f2.getParentId()) {
                return -1;
            } else {
                return f1.getCreatedDate().compareTo(f2.getCreatedDate());
            }
        }
        return 0;
    }
}
