package com.arton.backend.administer.artist.application.service;

import com.arton.backend.administer.artist.application.data.ArtistAdminCreateDTO;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminSaveUseCase;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.file.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistAdminService implements ArtistAdminSaveUseCase {

    private final ArtistRepositoryPort artistRepositoryPort;
    private final FileUploadUtils fileUploadUtils;
    @Value("${spring.artist.image.dir}")
    private String artistDir;

    @Override
    public Artist addArtist(ArtistAdminCreateDTO artistAdminCreateDTO) {
        Artist artist = artistAdminCreateDTO.dtoToDomain();
        Artist savedArtist = artistRepositoryPort.save(artist);

        String imageUrl = fileUploadUtils.upload(artistAdminCreateDTO.getImage(), artistDir + savedArtist.getId());

        savedArtist.setProfileImageUrl(imageUrl);

        return artistRepositoryPort.save(savedArtist);
    }
}
