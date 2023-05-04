package com.arton.backend.administer.artist.adapter.in;

import com.arton.backend.administer.artist.application.data.ArtistAdminCreateDTO;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminSaveUseCase;
import com.arton.backend.artist.domain.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ArtistAdminController {
    private final ArtistAdminSaveUseCase artistAdminSaveUseCase;

    @PostMapping("/web/artist/add")
    @ResponseBody
    public ResponseEntity<Artist> saveArtist(@Valid ArtistAdminCreateDTO artistAdminCreateDTO) {
        Artist artist = artistAdminSaveUseCase.addArtist(artistAdminCreateDTO);
        return ResponseEntity.ok(artist);
    }
}
