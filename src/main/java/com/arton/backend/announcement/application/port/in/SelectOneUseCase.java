package com.arton.backend.announcement.application.port.in;


import com.arton.backend.announcement.domain.Announcement;

public interface SelectOneUseCase {

    Announcement loadAnnouncementDetail(long id);
}
