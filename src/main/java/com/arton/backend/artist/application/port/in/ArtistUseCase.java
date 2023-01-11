package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.domain.Artist;

import java.util.List;

public interface ArtistUseCase {
    List<ArtistZzimDto> showArtistListForZzim(String type);
    List<ArtistZzimDto> showArtistListForZzim();
    Artist save(Artist artist);
}
