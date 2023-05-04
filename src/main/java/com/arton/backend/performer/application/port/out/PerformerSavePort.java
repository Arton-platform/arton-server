package com.arton.backend.performer.application.port.out;

import com.arton.backend.performer.domain.Performer;

import java.util.List;

public interface PerformerSavePort {
    Performer save(Performer performer);
    void saveAll(List<Performer> performers);
}
