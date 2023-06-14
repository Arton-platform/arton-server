package com.arton.backend.performer.application.port.in;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.Performance;

import java.util.List;

public interface PerformerSaveUseCase {
    void savePerformers(Performance performance, List<Artist> artists);
}
