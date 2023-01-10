package com.arton.backend.performer.domain;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.performance.domain.Performance;
import lombok.*;

import javax.persistence.*;

/**
 * 출연자
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Performer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 아티스트 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;
    /** 공연 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;
}
