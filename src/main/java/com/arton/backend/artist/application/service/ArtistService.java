package com.arton.backend.artist.application.service;

import com.arton.backend.artist.application.data.*;
import com.arton.backend.artist.application.port.in.ArtistUseCase;
import com.arton.backend.artist.application.port.in.CrawlerEnrollUseCase;
import com.arton.backend.artist.application.port.in.SpotifyEnrollUseCase;
import com.arton.backend.artist.application.port.out.ArtistRepositoryPort;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.spotify.SpotifyService;
import com.arton.backend.performance.domain.PerformanceType;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService implements ArtistUseCase, SpotifyEnrollUseCase, CrawlerEnrollUseCase {
    private final ArtistRepositoryPort artistRepositoryPort;
    private final SpotifyService spotifyService;

    @Override
    public ArtistInterestDetailDTO showArtistListForZzim(Pageable pageable) {
        List<ArtistInterestDto> concerts = artistRepositoryPort.findByPerformanceType(PerformanceType.CONCERT, pageable)
                .stream()
                .map(ArtistInterestDto::of)
                .collect(Collectors.toList());

        List<ArtistInterestDto> musicals = artistRepositoryPort.findByPerformanceType(PerformanceType.MUSICAL, pageable)
                .stream()
                .map(ArtistInterestDto::of)
                .collect(Collectors.toList());

        return ArtistInterestDetailDTO.builder().concerts(concerts).musicals(musicals).build();
    }

    @Override
    public Page<Artist> findAll(Pageable pageable) {
        return artistRepositoryPort.findAll(pageable);
    }

    @Override
    public List<Artist> findAll() {
        return artistRepositoryPort.findAll();
    }

    @Override
    public List<CommonArtistDto> findByName(String name) {
        return artistRepositoryPort.findByName(name).stream().map(CommonArtistDto::domainToDto).collect(Collectors.toList());
    }

    @Override
    public List<CommonArtistDto> findAllByPage(Pageable pageable) {
        return artistRepositoryPort.findAllByPage(pageable).stream().map(CommonArtistDto::domainToDto).collect(Collectors.toList());
    }

    @Override
    public Artist save(Artist artist) {
        return artistRepositoryPort.save(artist);
    }

    @Override
    public void enrollArtistBySpotify(SpotifyRegistDTO spotifyRegistDTO) {
        String names = spotifyRegistDTO.getNames();
        String regexNames = names.replaceAll("[^a-zA-Z0-9ㄱ-ㅎ가-힣,]", "");
        String[] split = regexNames.split(",");
        for (String name : split) {
            if (!StringUtils.hasText(name))
                continue;
            JsonObject artistsAsync = spotifyService.getArtistsAsync(name);
            Artist artist = Artist.builder().age(0).profileImageUrl(artistsAsync.get("imageUrl").getAsString()).name(name).build();
            artistRepositoryPort.save(artist);
        }
    }

    @Override
    public void enrollArtistByCrawler(CrawlerArtistRegistDTO crawlerArtistRegistDTO) {
        Boolean checkDup = artistRepositoryPort.checkDup(crawlerArtistRegistDTO.getName(), crawlerArtistRegistDTO.getProfileImageUrl());
        if (!checkDup) {
            Artist artist = crawlerArtistRegistDTO.mapToDomainFromDTO();
            artistRepositoryPort.save(artist);
        }
    }

    @Override
    public List<Artist> enrollArtistsByCrawler(List<Artist> artistList) {
        List<Artist> artists = new ArrayList<>();
        for (Artist artist : artistList) {
            Artist found = artistRepositoryPort.findByNameAndUrl(artist.getName(), artist.getProfileImageUrl()).orElse(null);
            // 기존재시 찾은거 등록
            if (found != null) {
                artists.add(found);
            }else {
                System.out.println(" save artist");
                Artist save = artistRepositoryPort.save(artist);
                System.out.println("save = " + save.getName());
                artists.add(save);
            }
        }
        return artists;
    }
}
