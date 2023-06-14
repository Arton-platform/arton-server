package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.artist.application.data.CrawlerArtistRegistDTO;
import com.arton.backend.artist.application.service.ArtistService;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.image.application.port.out.PerformanceImageSaveRepositoryPort;
import com.arton.backend.image.domain.PerformanceImage;
import com.arton.backend.infra.utils.LocalDateTimeConverter;
import com.arton.backend.performance.applicaiton.data.CrawlerPerformanceCreateDTO;
import com.arton.backend.performance.applicaiton.port.in.CrawlerPerformanceSaveUseCase;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performer.application.service.PerformerService;
import com.arton.backend.price.application.data.CrawlerPriceCreateDTO;
import com.arton.backend.price.application.port.out.PriceGradeSavePort;
import com.arton.backend.price.domain.PriceGrade;
import com.arton.backend.search.application.port.out.PerformanceDocuemntSavePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CrawlerPerformanceService implements CrawlerPerformanceSaveUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final PerformanceSavePort performanceSavePort;
    private final PerformanceDocuemntSavePort performanceDocuemntSavePort;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");
    private final PriceGradeSavePort priceGradeSavePort;
    private final PerformanceImageSaveRepositoryPort performanceImageSaveRepositoryPort;
    private final ArtistService artistService;
    private final PerformerService performerService;

    /**
     * 하나의 공연에 대해 모든 과정이 완료되어야함.
     * 따라서 event 분리하지 않고 하나의 트랜잭션으로 묶음.
     * @param crawlerPerformanceCreateDTO
     */
    @Override
    public void addByCrawler(CrawlerPerformanceCreateDTO crawlerPerformanceCreateDTO) {
        log.info("crawler request {}", crawlerPerformanceCreateDTO);
        // get local date time
        LocalDateTime startDate = LocalDateTimeConverter.strToDate(crawlerPerformanceCreateDTO.getStartDate());
        // 존재하는지 체크
        boolean exist = performanceRepositoryPort.existByTitleAndStartDate(crawlerPerformanceCreateDTO.getTitle(), startDate);
        if (exist) {
            return;
        } else {
            // 미존재시 등록 시작.
            // add
            Performance performance = performanceSavePort.save(crawlerPerformanceCreateDTO.toDomainFromDTO());
            // document synchronize
            performanceDocuemntSavePort.save(performance);
            // get images
            String descriptionUrl = crawlerPerformanceCreateDTO.getDescription();
            String imageUrl = crawlerPerformanceCreateDTO.getImageUrl();
            List<PerformanceImage> performanceImages = new ArrayList<>();
            // add description url
            performanceImages.add(PerformanceImage.builder()
                    .performance(performance)
                    .imageUrl(descriptionUrl).build());
            // add image url
            performanceImages.add(PerformanceImage.builder()
                    .performance(performance)
                    .imageUrl(imageUrl).build());
            // save image
            performanceImageSaveRepositoryPort.saveAll(performanceImages);
            // save prices
            List<CrawlerPriceCreateDTO> grades = crawlerPerformanceCreateDTO.getGrades();
            if (!ObjectUtils.isEmpty(grades)) {
                for (CrawlerPriceCreateDTO grade : grades) {
                    PriceGrade priceGrade = grade.toDomainFromDTO();
                    priceGrade.assignPriceToPerformance(performance);
                    PriceGrade savedPrice = priceGradeSavePort.save(priceGrade);
                    performance.getPriceGradeList().add(savedPrice);
                }
            }
            // save artist and performers
            List<Artist> artists = Optional.ofNullable(crawlerPerformanceCreateDTO.getArtists()).orElseGet(Collections::emptyList).stream().map(CrawlerArtistRegistDTO::mapToDomainFromDTO).collect(Collectors.toList());
            List<Artist> artistList = new ArrayList<>();
            if (!ObjectUtils.isEmpty(artists)) {
                artistList = artistService.enrollArtistsByCrawler(artists);
                performerService.savePerformers(performance, artistList);
            }
            // update
            performanceSavePort.save(performance);
        }
    }
}
