package com.arton.backend.announcement.domain;

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

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Announcement extends Board {
    private long announcementId;
    private String title;
    private String content;

    @Builder
    public Announcement(long announcementId, String title, String content, User user, int hit, Image image, LocalDateTime createdDate, LocalDateTime updateDate){
        super(user,hit,image,createdDate,updateDate);
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
    }
}
