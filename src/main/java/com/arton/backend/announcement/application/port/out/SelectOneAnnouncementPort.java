package com.arton.backend.announcement.application.port.out;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementEntity;

import java.util.Optional;

public interface SelectOneAnnouncementPort {

    Optional<AnnouncementEntity> findById(long id);
}
