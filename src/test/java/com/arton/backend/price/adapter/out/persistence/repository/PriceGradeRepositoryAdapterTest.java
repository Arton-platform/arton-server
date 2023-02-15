package com.arton.backend.price.adapter.out.persistence.repository;

import com.arton.backend.price.application.port.out.PriceGradeRepositoryPort;
import com.arton.backend.price.domain.PriceGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceGradeRepositoryAdapterTest {

    @Autowired
    private PriceGradeRepositoryPort priceGradeRepositoryPort;

    @Test
    void find() {
        Optional<PriceGrade> byId = priceGradeRepositoryPort.findById(1L);
    }
}