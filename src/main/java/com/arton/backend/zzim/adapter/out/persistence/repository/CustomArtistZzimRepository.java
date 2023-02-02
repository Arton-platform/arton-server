package com.arton.backend.zzim.adapter.out.persistence.repository;

import com.arton.backend.zzim.domain.ArtistZzim;

import java.util.List;

public interface CustomArtistZzimRepository {
    List<ArtistZzim> getUsersFavoriteArtists(Long userId);
    long deleteUsersFavoriteArtists(Long userId, List<Long> ids);
}
