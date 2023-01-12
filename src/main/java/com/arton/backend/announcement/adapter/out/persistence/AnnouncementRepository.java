package com.arton.backend.announcement.adapter.out.persistence;

import com.arton.backend.announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
