package com.arton.backend.zzim.application.port.out;

import com.arton.backend.zzim.domain.ArtistZzim;
import com.arton.backend.zzim.domain.PerformanceZzim;

import java.util.List;

public interface ZzimRepositoryPort {
    List<ArtistZzim> saveArtists(List<ArtistZzim> artistZzims);
    List<PerformanceZzim> savePerformances(List<PerformanceZzim> performanceZzims);
}
