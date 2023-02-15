package com.arton.backend.price.application.port.out;

import com.arton.backend.price.domain.PriceGrade;

public interface PriceGradeSavePort {
    PriceGrade save(PriceGrade priceGrade);
}
