package com.arton.backend.announcement.adapter.out.persistence;

import com.arton.backend.announcement.domain.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {

    public Announcement toDomain(AnnouncementEntity entity){
        return Announcement.builder()
                .announcementId(entity.getAnnouncementId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .hit(entity.getHit())
                .image(entity.getImage())
                .user(entity.getUser())
                .createdDate(entity.getCreatedDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    public AnnouncementEntity toEntity(Announcement announcement){
        return AnnouncementEntity.builder()
                .announcementId(announcement.getAnnouncementId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .hit(announcement.getHit())
                .image(announcement.getImage())
                .user(announcement.getUser())
                .createdDate(announcement.getCreatedDate())
                .updateDate(announcement.getUpdateDate())
                .build();
    }
}
