package com.arton.backend.announcement.application.port.out;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementEntity;

import java.util.List;
import java.util.Optional;

public interface SelectAllAnnouncementPort {
    Optional<List<AnnouncementEntity>> findAllByOrderByCreatedDateDesc();
}
