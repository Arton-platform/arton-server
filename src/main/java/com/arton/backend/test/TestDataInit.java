package com.arton.backend.test;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.adapter.out.repository.ArtistRepository;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceRepository;
import com.arton.backend.performance.domain.PerformanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 테스트 데이터 삽입용
 */
@RequiredArgsConstructor
public class TestDataInit {
    private final ArtistRepository artistRepository;
    private final PerformanceRepository performanceRepository;
    private String defaultImage = "image/profiles/default.png";

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Random random = new Random();
        List<ArtistEntity> artistList = new ArrayList<>();
        List<PerformanceEntity> performances = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ArtistEntity artist = ArtistEntity.builder()
                    .age(random.nextInt(30) + i)
                    .name("test" + i)
                    .profileImageUrl(defaultImage)
                    .snsId("abc" + i + "@naver.com")
                    .performances(new ArrayList<>())
                    .build();
            artistList.add(artist);

            PerformanceEntity performance = PerformanceEntity.builder()
                    .performanceType(i % 2 == 0 ? PerformanceType.MUSICAL : PerformanceType.CONCERT)
                    .description("test" + i)
                    .title("test" + i)
                    .performers(new ArrayList<>())
                    .priceGradeList(new ArrayList<>())
                    .build();
            performances.add(performance);
        }

        artistRepository.saveAll(artistList);
        performanceRepository.saveAll(performances);
    }
}
