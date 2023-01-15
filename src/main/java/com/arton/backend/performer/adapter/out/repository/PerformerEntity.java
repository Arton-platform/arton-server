package com.arton.backend.performer.adapter.out.repository;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.adapter.out.repository.PerformanceEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performer.domain.Performer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 아티스트 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;
    /** 공연 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performance;

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

    @Builder
    public PerformerEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, ArtistEntity artist, PerformanceEntity performance) {
        super(createdDate, updateDate);
        this.id = id;
        this.artist = artist;
        this.performance = performance;
    }
}
