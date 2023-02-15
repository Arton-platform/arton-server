package com.arton.backend.price.application.port.out;

import com.arton.backend.price.domain.PriceGrade;

import java.util.List;

public interface PriceGradeSavePort {
    PriceGrade save(PriceGrade priceGrade);
    void saveAll(List<PriceGrade> priceGradeList);
}
