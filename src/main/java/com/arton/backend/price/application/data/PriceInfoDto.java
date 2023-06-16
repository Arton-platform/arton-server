package com.arton.backend.price.application.data;

import com.arton.backend.price.domain.PriceGrade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.ObjectUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Schema(description = "가격 정보 DTO")
public class PriceInfoDto {
    private String gradeName;
    private Long price;

    @Builder
    @QueryProjection
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

    @JsonIgnore
    public boolean isCompleted() {
        return !ObjectUtils.isEmpty(gradeName) && !ObjectUtils.isEmpty(price);
    }

}
