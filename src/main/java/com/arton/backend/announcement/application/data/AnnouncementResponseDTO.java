package com.arton.backend.announcement.application.data;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementEntity;
import com.arton.backend.announcement.domain.Announcement;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "특정 공지사항 정보")
public class AnnouncementResponseDTO {
    private Long id;
    private String title;
    private String content;

    @Builder
    public AnnouncementResponseDTO(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static AnnouncementResponseDTO toDtoFromDomain(Announcement announcement) {
        return AnnouncementResponseDTO.builder()
                .id(announcement.getAnnouncementId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .build();
    }

    public static AnnouncementResponseDTO toDtoFromEntity(AnnouncementEntity announcement) {
        return AnnouncementResponseDTO.builder()
                .id(announcement.getAnnouncementId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .build();
    }
}
