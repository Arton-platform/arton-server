package com.arton.backend.artist.adapter.out.repository;

import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryAdapter implements ArtistRepositoryPort {
    private final ArtistRepository artistRepository;
}
