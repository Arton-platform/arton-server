package com.arton.backend.price.adapter.out.persistence.repository;

import com.arton.backend.performance.applicaiton.port.out.PerformanceSavePort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.price.application.port.out.PriceGradeRepositoryPort;
import com.arton.backend.price.application.port.out.PriceGradeSavePort;
import com.arton.backend.price.domain.PriceGrade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
class PriceGradeRepositoryAdapterTest {

    @Autowired
    private PriceGradeRepositoryPort priceGradeRepositoryPort;
    @Autowired
    private PriceGradeSavePort priceGradeSavePort;
    @Autowired
    private PerformanceSavePort performanceSavePort;

    @Test
    void find() {
        Optional<PriceGrade> byId = priceGradeRepositoryPort.findById(1L);
    }

    @Test
    void findByPerformanceIdTest() {
        Performance build = Performance.builder().performanceId(11111L).build();
        Performance save = performanceSavePort.save(build);
        List<PriceGrade> res = new ArrayList<>();
        PriceGrade a = PriceGrade.builder().price(1000L).gradeName("A").performance(save).build();
        PriceGrade s = PriceGrade.builder().price(2000L).gradeName("S").performance(save).build();
        PriceGrade r = PriceGrade.builder().price(3333L).gradeName("R").performance(save).build();
        res.add(a);
        res.add(s);
        res.add(r);
        priceGradeSavePort.saveAll(res);
        List<PriceGrade> byPerformanceId = priceGradeRepositoryPort.findByPerformanceId(save.getPerformanceId());
        for (PriceGrade priceGrade : byPerformanceId) {
            System.out.println("priceGrade = " + priceGrade);
        }
        Assertions.assertThat(byPerformanceId.size()).isEqualTo(res.size());
    }
}