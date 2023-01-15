package com.arton.backend.performer.domain;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.domain.Performance;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 출연자
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performer{
    private Long id;
    /** 아티스트 */
    private Artist artist;
    /** 공연 */
    private Performance performance;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updateDate;
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Builder
    public Performer(Long id, Artist artist, Performance performance, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.artist = artist;
        this.performance = performance;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
