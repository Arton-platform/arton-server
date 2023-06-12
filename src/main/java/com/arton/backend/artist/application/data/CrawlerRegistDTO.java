package com.arton.backend.artist.application.data;

import com.arton.backend.artist.domain.Artist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 찜하기에 보여주는 용도
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrawlerRegistDTO {
    /**
     * 아티스트 이름
     */
    @NotBlank
    private String name;
    @NotBlank
    private String profileImageUrl;
    @NotBlank
    private Integer age;
    @NotBlank
    private String snsId;

    @Builder
    public CrawlerRegistDTO(String name, String profileImageUrl, Integer age, String snsId) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.age = age;
        this.snsId = snsId;
    }

    public Artist mapToDomainFromDTO() {
        return Artist.builder()
                .name(this.name)
                .profileImageUrl(this.profileImageUrl)
                .age(this.age)
                .snsId(this.snsId).build();
    }
}
