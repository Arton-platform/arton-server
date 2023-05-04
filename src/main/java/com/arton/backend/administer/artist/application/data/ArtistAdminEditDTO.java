package com.arton.backend.administer.artist.application.data;

import com.arton.backend.administer.performance.application.data.PerformanceAdminEditDto;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.Performance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "아티스트 수정을 위한 DTO")
public class ArtistAdminEditDTO {
    @Schema(description = "이름")
    private String name;
    @Schema(description = "나이")
    private Integer age;
    @Schema(description = "SNS ID")
    private String snsId;

    @Builder
    public ArtistAdminEditDTO(String name, Integer age, String snsId) {
        this.name = name;
        this.age = age;
        this.snsId = snsId;
    }

    public static ArtistAdminEditDTO domainToDto(Artist artist) {
        return ArtistAdminEditDTO.builder()
                .name(artist.getName())
                .age(artist.getAge())
                .snsId(artist.getSnsId())
                .build();
    }
}
