package com.arton.backend.artist.domain;

import com.arton.backend.performer.domain.Performer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Artist {
    private Long id;
    /** 아티스트 이름 */
    private String name;
    /** 나이 */
    private Integer age;
    /** sns id */
    private String snsId;
    /** 아티스트 이미지 링크 */
    private String profileImageUrl;
    /** 가입일 */
    private LocalDateTime createdDate;
    /** 수정일 */
    private LocalDateTime updateDate;
    /** 아티스트의 musical or concert 작품 */
    @ToString.Exclude
    private List<Performer> performances = new ArrayList<>();

    public void addPerformer(Performer performer) {
        performances.add(performer);
        performer.setArtist(this.getId());
    }

    @Builder
    public Artist(Long id, String name, Integer age, String snsId, String profileImageUrl, LocalDateTime createdDate, LocalDateTime updateDate, List<Performer> performances) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.snsId = snsId;
        this.profileImageUrl = profileImageUrl;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.performances = performances;
    }
}
