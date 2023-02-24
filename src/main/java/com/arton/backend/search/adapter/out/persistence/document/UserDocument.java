package com.arton.backend.search.adapter.out.persistence.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "user")
@Mapping(mappingPath = "static/elastic/user-mapping.json")
@Setting(settingPath = "static/elastic/user-setting.json")
public class UserDocument {
    @Id
    private Long id;
    /** 닉네임 */
    private String nickname;
    /** 이메일 */
    private String email;
    /** 가입일 */
    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdDate;
    /** 마케팅 동의 */
    private String termsAgree;

    @Builder
    public UserDocument(Long id, String nickname, String email, LocalDateTime createdDate, String termsAgree) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.createdDate = createdDate;
        this.termsAgree = termsAgree;
    }
}
