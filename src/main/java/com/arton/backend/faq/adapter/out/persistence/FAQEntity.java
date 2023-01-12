package com.arton.backend.faq.adapter.out.persistence;

import com.arton.backend.common.entity.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="faq")
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
