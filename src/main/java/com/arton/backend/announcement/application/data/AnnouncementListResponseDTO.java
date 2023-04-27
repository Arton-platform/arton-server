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
@Schema(description = "공지사항 list api 요청 정보")
public class AnnouncementListResponseDTO {
    private Long id;
    private String title;

    @Builder
    public AnnouncementListResponseDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static AnnouncementListResponseDTO toDtoFromDomain(Announcement announcement) {
        return AnnouncementListResponseDTO.builder()
                .id(announcement.getAnnouncementId())
                .title(announcement.getTitle())
                .build();
    }

    public static AnnouncementListResponseDTO toDtoFromEntity(AnnouncementEntity announcement) {
        return AnnouncementListResponseDTO.builder()
                .id(announcement.getAnnouncementId())
                .title(announcement.getTitle())
                .build();
    }
}
