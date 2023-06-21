package com.arton.backend.zzim.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 공연 또는 아티스트 아이디를 전달
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "공연/아티스트 찜하기에 사용할 DTO")
public class ZzimCreateDto {
    @Schema(description = "공연/아티스트 ID")
    private Long id;
}
