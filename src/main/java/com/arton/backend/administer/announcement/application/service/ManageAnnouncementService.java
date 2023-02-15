package com.arton.backend.administer.announcement.application.service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arton.backend.administer.announcement.application.port.in.ManageAnnouncementUseCase;
import com.arton.backend.administer.announcement.application.port.out.ManageAnnouncementPort;
import com.arton.backend.announcement.adapter.out.persistence.AnnouncementEntity;
import com.arton.backend.announcement.adapter.out.persistence.AnnouncementMapper;
import com.arton.backend.announcement.domain.Announcement;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class ManageAnnouncementService implements ManageAnnouncementUseCase {
    
    private final ManageAnnouncementPort manageAnnouncementPort;
    private final AnnouncementMapper announcementMapper;


    @Override
    public List<Announcement> allAnnouncement() {
        return manageAnnouncementPort.allAnnouncement().orElseGet(ArrayList::new).stream()
                .map(announcementMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Announcement getAnnouncement(Long id) {
        return  announcementMapper.toDomain(
            manageAnnouncementPort.getAnnouncement(id).orElseThrow(() -> new CustomException("존재하지 않는 공지사항입니다.", ErrorCode.SELECT_ERROR))
        );
    }

    @Override
    public void announcementRegist(Announcement announcement) {
        manageAnnouncementPort.announcementRegist(
            announcementMapper.toEntity(announcement)
        );
    }

    @Override
    public void announcementUpdate(Announcement announcement) {
        AnnouncementEntity original = manageAnnouncementPort.getAnnouncement(announcement.getAnnouncementId())
                .orElseThrow(() -> new CustomException("",ErrorCode.SELECT_ERROR));
        
        original.changeTitle(announcement.getTitle())
                .changeContent(announcement.getContent());
    }

    @Override
    public void announcementDelete(Long id) {
        manageAnnouncementPort.announcementDelete(id);
    }

}
