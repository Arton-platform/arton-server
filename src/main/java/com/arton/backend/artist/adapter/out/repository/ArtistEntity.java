package com.arton.backend.artist.adapter.out.repository;

import com.arton.backend.artist.domain.Artist;
import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performer.adapter.out.repository.PerformerEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class ArtistEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 아티스트 이름 */
    private String name;
    /** 나이 */
    private Integer age;
    /** sns id */
    private String snsId;
    /** 아티스트 이미지 링크 */
    private String profileImageUrl;
    /** 아티스트의 musical or concert 작품 */
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<PerformerEntity> performances = new ArrayList<>();

    public void addPerformer(PerformerEntity performer) {
        performances.add(performer);
        performer.setArtist(this);
    }

}
