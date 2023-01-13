package com.arton.backend.announcement.adapter.out.persistence;

import com.arton.backend.announcement.application.port.in.SelectOneUseCase;
import com.arton.backend.announcement.application.port.out.SelectAllAnnouncementPort;
import com.arton.backend.announcement.application.port.out.SelectOneAnnouncementPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnnouncementPersistenceAdapter implements SelectAllAnnouncementPort, SelectOneAnnouncementPort {

    private final AnnouncementRepository repository;

    @Override
    public Optional<List<AnnouncementEntity>> findAllByOrderByCreatedDateDesc() {
        return repository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public Optional<AnnouncementEntity> findById(long id) {
        return repository.findById(id);
    }
}
