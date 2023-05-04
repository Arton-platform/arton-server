package com.arton.backend.administer.artist.application.service;

import com.arton.backend.administer.artist.application.data.ArtistAdminCreateDTO;
import com.arton.backend.administer.artist.application.data.ArtistAdminEditDTO;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminDeleteUseCase;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminEditUseCase;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminSaveUseCase;
import com.arton.backend.administer.artist.application.port.in.ArtistAdminUseCase;
import com.arton.backend.artist.application.port.out.ArtistDeletePort;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.file.FileUploadUtils;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistAdminService implements ArtistAdminSaveUseCase, ArtistAdminUseCase, ArtistAdminEditUseCase, ArtistAdminDeleteUseCase {

    private final ArtistRepositoryPort artistRepositoryPort;
    private final ArtistDeletePort artistDeletePort;
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

    @Override
    public ArtistAdminEditDTO getArtistEditDto(Long artistId) {
        Artist artist = artistRepositoryPort.findById(artistId).orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND.getMessage(), ErrorCode.ARTIST_NOT_FOUND));
        return ArtistAdminEditDTO.domainToDto(artist);
    }

    @Override
    public void editArtist(Long id, ArtistAdminEditDTO artistAdminEditDTO) {
        Artist artist = artistRepositoryPort.findById(id).orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND.getMessage(), ErrorCode.ARTIST_NOT_FOUND));
        artist.editArtist(artistAdminEditDTO);
        // domain 반영
        artistRepositoryPort.save(artist);
    }

    @Override
    public void deleteArtist(Long artistId) {
        // imageUrl 삭제
        Artist artist = artistRepositoryPort.findById(artistId).orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND.getMessage(), ErrorCode.ARTIST_NOT_FOUND));
        fileUploadUtils.deleteFile(artistId, artist.getProfileImageUrl());
        // 공연 제거
        artistDeletePort.deleteById(artistId);
    }
}
