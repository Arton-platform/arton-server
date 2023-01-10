package com.arton.backend.artist.application.port.out;

import com.arton.backend.artist.domain.Artist;

import java.util.List;

public interface ArtistRepositoryPort {
    List<Artist> getAllArtist();
}
