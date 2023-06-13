package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.application.data.CrawlerArtistRegistDTO;

public interface CrawlerEnrollUseCase {
    void enrollArtistByCrawler(CrawlerArtistRegistDTO crawlerArtistRegistDTO);
}
