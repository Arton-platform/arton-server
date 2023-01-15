package com.arton.backend.performer.domain;

import com.arton.backend.artist.adapter.out.repository.ArtistEntity;
import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.performer.adapter.out.repository.PerformerEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 출연자
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Performer extends BaseEntity {
    /** 아티스트 */
    private ArtistEntity artist;
    /** 공연 */
    private PerformerEntity performance;
}
