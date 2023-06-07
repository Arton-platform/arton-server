package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.application.data.SpotifyRegistDTO;

public interface SpotifyEnrollUseCase {
    void enrollArtistBySpotify(SpotifyRegistDTO spotifyRegistDTO);
}
