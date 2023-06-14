package com.arton.backend.price.application.data;

import com.arton.backend.price.domain.PriceGrade;
import lombok.*;

@Getter
public class CrawlerPriceCreateDTO {
    private String gradeName;
    private String price;

    public PriceGrade toDomainFromDTO() {
        return PriceGrade.builder()
                .price(Long.parseLong(price))
                .gradeName(gradeName)
                .build();
    }

}
