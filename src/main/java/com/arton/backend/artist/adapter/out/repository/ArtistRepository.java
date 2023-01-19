package com.arton.backend.artist.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long>, CustomArtistRepository {
}
