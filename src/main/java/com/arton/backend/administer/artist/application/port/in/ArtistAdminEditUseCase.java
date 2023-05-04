package com.arton.backend.administer.artist.application.port.in;

import com.arton.backend.administer.artist.application.data.ArtistAdminEditDTO;

public interface ArtistAdminEditUseCase {
    void editArtist(Long id, ArtistAdminEditDTO artistAdminEditDTO);
}
