package com.arton.backend.artist.domain;

import com.arton.backend.performer.adapter.out.repository.PerformerEntity;
import com.arton.backend.performer.domain.Performer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Artist {
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
    /** 업데이트일 */
    private LocalDateTime updateDate;
    /** 아티스트의 musical or concert 작품 */
    private List<Performer> performances = new ArrayList<>();

    public void addPerformer(Performer performer) {
        performances.add(performer);
        performer.setArtist(this);
    }
}
