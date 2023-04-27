package com.arton.backend.announcement.application.port.in;


import com.arton.backend.announcement.application.data.AnnouncementResponseDTO;
import com.arton.backend.announcement.domain.Announcement;

public interface SelectOneUseCase {

    Announcement loadAnnouncementDetail(long id);
    /**
     * 동건님 요청사향 dto 반영
     * 20230427
     * @return
     */
    AnnouncementResponseDTO loadAnnouncementDetailV2(long id);
}
