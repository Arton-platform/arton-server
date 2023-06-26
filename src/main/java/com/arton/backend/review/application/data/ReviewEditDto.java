package com.arton.backend.review.application.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEditDto {
    private Long id;
    private String content;
    private Float startScore;
}
