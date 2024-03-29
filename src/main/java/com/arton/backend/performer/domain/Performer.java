package com.arton.backend.performer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * 출연자
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performer{
    private Long id;
    /** 아티스트 */
    private Long artist;
    /** 공연 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Long performance;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 업데이트일 */
    private LocalDateTime updatedDate;
    public void setArtist(Long artist) {
        this.artist = artist;
    }

    @Builder
    public Performer(Long id, Long artist, Long performance, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.artist = artist;
        this.performance = performance;
        this.createdDate = createdDate;
        this.updatedDate = updateDate;
    }
}
