package com.arton.backend.artist.adapter.out.persistence.repository;

import com.arton.backend.artist.adapter.out.persistence.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long>, CustomArtistRepository {
    List<ArtistEntity> findByName(String name);
    Boolean existsByNameAndProfileImageUrl(String name, String profileImageUrl);
}
