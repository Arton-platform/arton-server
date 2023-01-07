package com.arton.backend.repository.board;

import com.arton.backend.entity.board.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
