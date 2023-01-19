package com.arton.backend.zzim.adapter.out.repository;

import com.arton.backend.zzim.domain.ArtistZzim;

import java.util.List;

public interface CustomArtistZzimRepository {
    List<ArtistZzim> getUsersFavoriteArtists(Long userId);
}
