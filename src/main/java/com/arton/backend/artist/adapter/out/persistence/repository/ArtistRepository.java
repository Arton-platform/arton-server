package com.arton.backend.artist.adapter.out.persistence.repository;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long>, CustomArtistRepository {
    List<ArtistEntity> findByName(String name);
    Optional<ArtistEntity> findByNameAndProfileImageUrl(String name, String profileImageUrl);
    Boolean existsByNameAndProfileImageUrl(String name, String profileImageUrl);
}
