package com.arton.backend.administer.category.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRegistDto {
    Long id;

    int level; // 카테고리 트리 구조를 위한 계층 컬럼
    int lines; // 한 페이지에 몇개의 카테고리를 표현할지 결정하는 컬럼
    int width; // 한 카테고리에 몇개의 공연을 표현할지 결정하는 컬럼
    String url; // 분류 클릭시 이동할 페이지
}
