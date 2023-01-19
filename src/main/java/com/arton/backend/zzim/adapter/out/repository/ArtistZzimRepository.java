package com.arton.backend.zzim.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistZzimRepository extends JpaRepository<ArtistZzimEntity, Long>, CustomArtistZzimRepository {
}
