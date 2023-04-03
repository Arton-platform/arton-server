package com.arton.backend.administer.announcement.adapter.out;

import com.arton.backend.administer.announcement.application.port.out.ManageAnnouncementPort;
import com.arton.backend.announcement.adapter.out.persistence.AnnouncementEntity;
import com.arton.backend.announcement.adapter.out.persistence.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ManageAnnouncementAdapter implements ManageAnnouncementPort {

    private final AnnouncementRepository repository;

    @Override
    public Optional<List<AnnouncementEntity>> allAnnouncement() {
        return Optional.of(repository.findAll());
    }

    @Override
    public Optional<AnnouncementEntity> getAnnouncement(Long id) {
        return repository.findById(id);
    }

    @Override
    public void announcementRegist(AnnouncementEntity toEntity) {
        repository.save(toEntity);
    }

    @Override
    public void announcementUpdate(AnnouncementEntity toEntity) {

    }

    @Override
    public void announcementDelete(Long id) {
        repository.deleteById(id);
    }
}
