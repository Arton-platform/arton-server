package com.arton.backend.announcement.adapter.out.persistence;

import com.arton.backend.announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {
    Optional<List<AnnouncementEntity>> findAllByOrderByCreatedDateDesc();
}
