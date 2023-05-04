package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.application.data.ArtistInterestDetailDTO;
import com.arton.backend.artist.application.data.ArtistInterestDto;
import com.arton.backend.artist.domain.Artist;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtistUseCase {
    ArtistInterestDetailDTO showArtistListForZzim(Pageable pageable);
    Artist save(Artist artist);
}
