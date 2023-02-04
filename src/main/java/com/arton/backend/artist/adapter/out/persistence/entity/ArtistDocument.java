package com.arton.backend.artist.adapter.out.persistence.entity;

import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Document(indexName = "artist")
@Mapping(mappingPath = "elastic/artist-mapping.json")
@Setting(settingPath = "elastic/artist-setting.json")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtistDocument {
    @Id
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
    private List<PerformerEntity> performances = new ArrayList<>();
    /** 가입일 */
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdDate;
    /** 수정일 */
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime updateDate;

    @Builder
    public ArtistDocument(Long id, String name, Integer age, String snsId, String profileImageUrl, List<PerformerEntity> performances, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.snsId = snsId;
        this.profileImageUrl = profileImageUrl;
        this.performances = performances;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public static ArtistDocument from(ArtistEntity artist) {
        return ArtistDocument.builder()
                .createdDate(artist.getCreatedDate())
                .updateDate(artist.getUpdateDate())
                .age(artist.getAge())
                .name(artist.getName())
                .profileImageUrl(artist.getProfileImageUrl())
                .performances(Optional.ofNullable(artist.getPerformances()).orElseGet(Collections::emptyList))
                .snsId(artist.getSnsId())
                .id(artist.getId())
                .build();
    }
}
