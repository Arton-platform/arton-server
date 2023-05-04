package com.arton.backend.performer.adapter.out.persistence.repository;

import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import com.arton.backend.performer.adapter.out.persistence.mapper.PerformerMapper;
import com.arton.backend.performer.application.port.out.PerformerSavePort;
import com.arton.backend.performer.domain.Performer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.performer.adapter.out.persistence.mapper.PerformerMapper.toDomain;
import static com.arton.backend.performer.adapter.out.persistence.mapper.PerformerMapper.toEntity;

@Repository
@RequiredArgsConstructor
public class PerformerRepositoryAdapter implements PerformerSavePort {
    private final PerformerRepository performerRepository;
    @Override
    public Performer save(Performer performer) {
        return toDomain(performerRepository.save(toEntity(performer)));
    }

    @Override
    public void saveAll(List<Performer> performers) {
        List<PerformerEntity> performerEntities = Optional.ofNullable(performers).orElseGet(Collections::emptyList)
                .stream()
                .map(PerformerMapper::toEntity)
                .collect(Collectors.toList());
        performerRepository.saveAll(performerEntities);
    }
}
