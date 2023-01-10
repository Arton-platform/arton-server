package com.arton.backend.artist.adapter.out.repository;

import com.arton.backend.artist.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long>, CustomArtistRepository {
}
