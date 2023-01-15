package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.domain.Artist;

import java.util.List;

public interface ArtistUseCase {
    List<ArtistInterestDto> showArtistListForZzim(String type);
    ArtistEntity save(ArtistEntity artist);
}
