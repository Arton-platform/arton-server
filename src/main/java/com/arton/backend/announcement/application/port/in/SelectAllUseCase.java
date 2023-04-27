package com.arton.backend.announcement.application.port.in;

import com.arton.backend.announcement.application.data.AnnouncementListResponseDTO;
import com.arton.backend.announcement.domain.Announcement;

import java.util.List;

public interface SelectAllUseCase {
    List<Announcement> announcementList();

    /**
     * 동건님 요청사항 dto 반영
     * 20230427
     * @return
     */
    List<AnnouncementListResponseDTO> announcementListV2();
}
