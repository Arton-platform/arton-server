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
public class CrawlerArtistRegistDTO {
    /**
     * 아티스트 이름
     */
    @NotBlank
    private String name;
    @NotBlank
    private String profileImageUrl;
    @NotBlank
    private String age;
    @NotBlank
    private String snsId;

    @Builder
    public CrawlerArtistRegistDTO(String name, String profileImageUrl, String age, String snsId) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.age = age;
        this.snsId = snsId;
    }

    public Artist mapToDomainFromDTO() {
        return Artist.builder()
                .name(this.name)
                .profileImageUrl(this.profileImageUrl)
                .age(Integer.parseInt(this.age))
                .snsId(this.snsId).build();
    }
}
