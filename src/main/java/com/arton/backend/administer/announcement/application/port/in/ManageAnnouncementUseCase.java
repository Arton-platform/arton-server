package com.arton.backend.administer.announcement.application.port.in;

import java.util.List;

import com.arton.backend.announcement.domain.Announcement;

public interface ManageAnnouncementUseCase {

    List<Announcement> allAnnouncement();

    Announcement getAnnouncement(Long id);

    void announcementRegist(Announcement announcement);

    void announcementUpdate(Announcement announcement);

    void announcementDelete(Long id);
}
