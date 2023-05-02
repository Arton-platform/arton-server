package com.arton.backend.performance.applicaiton.service;

import com.arton.backend.performance.applicaiton.data.PerformanceZzimDetailDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureWebMvc
class PerformanceServiceTest {

    @Autowired
    PerformanceService performanceService;

    @Test
    void getPerformanceZzimTest() {
        Pageable pageable = PageRequest.of(0, 9);
        PerformanceZzimDetailDTO zzimListV2 = performanceService.getZzimListV2(pageable);
        System.out.println("zzimListV2 = " + zzimListV2);
        Assertions.assertThat(zzimListV2.getConcerts().size()).isEqualTo(9);
        Assertions.assertThat(zzimListV2.getMusicals().size()).isEqualTo(9);
    }
}