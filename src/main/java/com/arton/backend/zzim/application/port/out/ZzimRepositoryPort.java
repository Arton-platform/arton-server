package com.arton.backend.zzim.application.port.out;

import com.arton.backend.zzim.adapter.out.repository.ArtistZzimEntity;
import com.arton.backend.zzim.adapter.out.repository.PerformanceZzimEntity;
import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

public interface ZzimRepositoryPort {
    List<ArtistZzimEntity> saveArtists(List<ArtistZzimEntity> artistZzims);
    List<PerformanceZzimEntity> savePerformances(List<PerformanceZzimEntity> performanceZzims);
}
