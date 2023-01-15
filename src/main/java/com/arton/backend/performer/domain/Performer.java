package com.arton.backend.performer.domain;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.shared.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 출연자
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Performer{
    private Long id;
    /** 아티스트 */
    private Artist artist;
    /** 공연 */
    private Performer performance;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
