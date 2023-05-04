package com.arton.backend.administer.artist.application.port.in;

import com.arton.backend.administer.artist.application.data.ArtistAdminCreateDTO;
import com.arton.backend.artist.domain.Artist;

public interface ArtistAdminSaveUseCase {
    Artist addArtist(ArtistAdminCreateDTO artistAdminCreateDTO);
}
