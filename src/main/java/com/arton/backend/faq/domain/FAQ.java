package com.arton.backend.faq.domain;

import com.arton.backend.infra.shared.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FAQ extends Board {
    // VO와 같이 데이터를 정의하는부분
    private long faqId;
    private String title;
    private String content;

    @Builder
    public FAQ(long faqId, String title, String content, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.faqId = faqId;
        this.title = title;
        this.content = content;
    }
}
