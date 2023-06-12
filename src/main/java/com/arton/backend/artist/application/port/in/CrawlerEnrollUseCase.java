package com.arton.backend.artist.application.port.in;

import com.arton.backend.artist.application.data.CrawlerRegistDTO;

public interface CrawlerEnrollUseCase {
    void enrollArtistByCrawler(CrawlerRegistDTO crawlerRegistDTO);
}
