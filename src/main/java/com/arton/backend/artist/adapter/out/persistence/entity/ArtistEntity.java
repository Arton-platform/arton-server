package com.arton.backend.artist.adapter.out.persistence.entity;

import com.arton.backend.infra.shared.BaseEntity;
import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Artist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public ArtistEntity(LocalDateTime createdDate, LocalDateTime updateDate, Long id, String name, Integer age, String snsId, String profileImageUrl, List<PerformerEntity> performances) {
        super(createdDate, updateDate);
        this.id = id;
        this.name = name;
        this.age = age;
        this.snsId = snsId;
        this.profileImageUrl = profileImageUrl;
        this.performances = performances;
    }
}
