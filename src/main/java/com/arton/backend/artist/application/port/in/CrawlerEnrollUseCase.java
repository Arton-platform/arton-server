package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.application.data.CrawlerArtistRegistDTO;
import com.arton.backend.artist.domain.Artist;

import java.util.List;

public interface CrawlerEnrollUseCase {
    void enrollArtistByCrawler(CrawlerArtistRegistDTO crawlerArtistRegistDTO);
    List<Artist> enrollArtistsByCrawler(List<Artist> artistList);
}
