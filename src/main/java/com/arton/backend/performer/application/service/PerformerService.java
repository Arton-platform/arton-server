package com.arton.backend.performer.application.service;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performer.application.port.in.PerformerSaveUseCase;
import com.arton.backend.performer.application.port.out.PerformerSavePort;
import com.arton.backend.performer.domain.Performer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformerService implements PerformerSaveUseCase {
    private final PerformerSavePort performerSavePort;

    @Override
    public void savePerformers(Performance performance, List<Artist> artists) {
        // 출연자가 없으면 출연자 테이블은 없음.
        List<Performer> performers = new ArrayList<>();
        for (Artist artist : artists) {
            performers.add(Performer.builder().performance(performance.getPerformanceId()).artist(artist.getId()).build());
        }
        performerSavePort.saveAll(performers);
    }
}
