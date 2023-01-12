package com.arton.backend.faq.adapter.out;

import com.arton.backend.common.entity.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class FAQEntity extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long faqId;
    private String title;
    private String content;

    @Builder
    public FAQEntity(long faqId, String title, String content, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.faqId = faqId;
        this.title = title;
        this.content = content;
    }
}
