package com.arton.backend.artist.adapter.out.persistence.entity;

import com.arton.backend.performer.adapter.out.persistence.entity.PerformerEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
