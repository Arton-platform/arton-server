package com.arton.backend.price.application.data;

import com.arton.backend.price.domain.PriceGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Schema(description = "가격 정보 DTO")
public class PriceInfoDto {
    private String gradeName;
    private Long price;

    @Builder
    public PriceInfoDto(String gradeName, Long price) {
        this.gradeName = gradeName;
        this.price = price;
    }

    public static PriceInfoDto domainToDto(PriceGrade priceGrade) {
        return PriceInfoDto.builder()
                .gradeName(priceGrade.getGradeName())
                .price(priceGrade.getPrice())
                .build();
    }
}
