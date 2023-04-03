package com.arton.backend.administer.announcement.application.port.out;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementEntity;

import java.util.List;
import java.util.Optional;

public interface ManageAnnouncementPort {
    Optional<List<AnnouncementEntity>> allAnnouncement();

    Optional<AnnouncementEntity> getAnnouncement(Long id);

    void announcementRegist(AnnouncementEntity toEntity);

    void announcementUpdate(AnnouncementEntity toEntity);

    void announcementDelete(Long id);
}
