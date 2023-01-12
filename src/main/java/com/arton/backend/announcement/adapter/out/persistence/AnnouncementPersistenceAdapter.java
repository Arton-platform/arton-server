package com.arton.backend.announcement.adapter.out.persistence;

import com.arton.backend.announcement.application.port.out.SelectAllAnnouncementPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AnnouncementPersistenceAdapter implements SelectAllAnnouncementPort {

    private final AnnouncementRepository repository;

    @Override
    public Optional<List<AnnouncementEntity>> findAllByOrderByCreatedDateDesc() {
        return repository.findAllByOrderByCreatedDateDesc();
    }
}
