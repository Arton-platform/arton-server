package com.arton.backend.announcement.application.service;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementMapper;
import com.arton.backend.announcement.application.port.in.*;
import com.arton.backend.announcement.application.port.out.*;
import com.arton.backend.announcement.domain.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService implements SelectAllUseCase, SelectOneUseCase, RegistUseCase, UpdateUseCase, DeleteUseCase {

    private final SelectAllAnnouncementPort selectAllAnnouncementPort;
//    private final SelectOneAnnouncementPort selectOneAnnouncementPort;
//    private final RegistAnnouncementPort registAnnouncementPort;
//    private final UpdateAnnouncementPort updateAnnouncementPort;
//    private final DeleteAnnouncementPort deleteAnnouncementPort;
    private final AnnouncementMapper announcementMapper;

    public List<Announcement> announcementList(){
        return selectAllAnnouncementPort.findAllByOrderByCreatedDateDesc()
                .map(announcementEntities -> announcementEntities
                        .stream()
                        .map(entity -> announcementMapper.toDomain(entity))
                        .collect(Collectors.toList())
                ).orElseGet(ArrayList::new);
    }

}
