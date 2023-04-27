package com.arton.backend.announcement.application.service;

import com.arton.backend.announcement.adapter.out.persistence.AnnouncementMapper;
import com.arton.backend.announcement.application.data.AnnouncementListResponseDTO;
import com.arton.backend.announcement.application.data.AnnouncementResponseDTO;
import com.arton.backend.announcement.application.port.in.*;
import com.arton.backend.announcement.application.port.out.SelectAllAnnouncementPort;
import com.arton.backend.announcement.application.port.out.SelectOneAnnouncementPort;
import com.arton.backend.announcement.domain.Announcement;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService implements SelectAllUseCase, SelectOneUseCase, RegistUseCase, UpdateUseCase, DeleteUseCase {

    private final SelectAllAnnouncementPort selectAllAnnouncementPort;
    private final SelectOneAnnouncementPort selectOneAnnouncementPort;
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

    @Override
    public List<AnnouncementListResponseDTO> announcementListV2() {
        return selectAllAnnouncementPort.findAllByOrderByCreatedDateDesc()
                .map(announcementEntities -> announcementEntities.stream()
                        .map(AnnouncementListResponseDTO::toDtoFromEntity)
                        .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public Announcement loadAnnouncementDetail(long id) {
        return selectOneAnnouncementPort.findById(id)
                .map(announcement -> announcementMapper.toDomain(announcement))
                .orElseThrow(() -> new CustomException("loadAnnouncementDetail", ErrorCode.SELECT_ERROR));
    }

    @Override
    public AnnouncementResponseDTO loadAnnouncementDetailV2(long id) {
        return selectOneAnnouncementPort.findById(id)
                .map(AnnouncementResponseDTO::toDtoFromEntity)
                .orElseThrow(() -> new CustomException("loadAnnouncementDetail", ErrorCode.SELECT_ERROR));
    }
}
