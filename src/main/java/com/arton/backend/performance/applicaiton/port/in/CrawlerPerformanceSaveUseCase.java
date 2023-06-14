package com.arton.backend.performance.applicaiton.port.in;

import com.arton.backend.performance.applicaiton.data.CrawlerPerformanceCreateDTO;

public interface CrawlerPerformanceSaveUseCase {
    void addByCrawler(CrawlerPerformanceCreateDTO crawlerPerformanceCreateDTO);
}
