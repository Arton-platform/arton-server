package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.application.data.ArtistInterestDetailDTO;
import com.arton.backend.artist.application.data.ArtistInterestDto;
import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.artist.domain.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtistUseCase {
    ArtistInterestDetailDTO showArtistListForZzim(Pageable pageable);
    Page<Artist> findAll(Pageable pageable);
    List<Artist> findAll();
    List<CommonArtistDto> findByName(String name);
    List<CommonArtistDto> findAllByPage(Pageable pageable);
    Artist save(Artist artist);
}
