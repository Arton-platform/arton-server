package com.arton.backend.administer.artist.application.data;

import com.arton.backend.artist.domain.Artist;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "아티스트 생성을 위한 DTO")
public class ArtistAdminCreateDTO {
    @Schema(description = "이름")
    @NotBlank
    private String name;
    @Schema(description = "나이")
    private Integer age;
    @Schema(description = "SNS ID")
    private String snsId;
    @Schema(description = "이미지 파일")
    private MultipartFile image;

    @Builder
    public ArtistAdminCreateDTO(String name, Integer age, String snsId, MultipartFile image) {
        this.name = name;
        this.age = age;
        this.snsId = snsId;
        this.image = image;
    }

    public Artist dtoToDomain() {
        return Artist.builder()
                .age(age)
                .snsId(snsId)
                .name(name)
                .build();
    }
}
