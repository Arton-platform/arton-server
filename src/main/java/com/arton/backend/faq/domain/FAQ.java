package com.arton.backend.faq.domain;

import com.arton.backend.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FAQ extends Board {
    // VO와 같이 데이터를 정의하는부분
    private long faqId;
    private String title;
    private String content;

}
