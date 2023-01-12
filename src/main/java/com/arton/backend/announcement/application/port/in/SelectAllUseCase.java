package com.arton.backend.announcement.application.port.in;

import com.arton.backend.announcement.domain.Announcement;

import java.util.List;

public interface SelectAllUseCase {
    List<Announcement> announcementList();
}
