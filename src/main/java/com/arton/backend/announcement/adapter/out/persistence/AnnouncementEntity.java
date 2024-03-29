package com.arton.backend.announcement.adapter.out.persistence;

import com.arton.backend.infra.shared.Board;
import com.arton.backend.image.domain.Image;
import com.arton.backend.user.adapter.out.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="announcement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementEntity extends Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long announcementId;
    private String title;
    private String content;

    public AnnouncementEntity changeTitle(String title){
        this.title = title;
        return this;
    }

    public AnnouncementEntity changeContent(String content){
        this.content = content;
        return this;
    }


    @Builder
    public AnnouncementEntity(long announcementId, String title, String content, UserEntity user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate) {
        super(user, hit, image, createdDate, updateDate);
        this.announcementId =announcementId;
        this.title = title;
        this.content = content;
    }
}
