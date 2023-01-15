package com.arton.backend.artist.domain;

import com.arton.backend.performer.adapter.out.repository.PerformerEntity;
import lombok.*;

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
    /** 아티스트의 musical or concert 작품 */
    private List<PerformerEntity> performances = new ArrayList<>();
}
