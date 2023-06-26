package com.arton.backend.review.application.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEditDto {
    private Long id;
    private String content;
    @PositiveOrZero
    private Float startScore;
}
