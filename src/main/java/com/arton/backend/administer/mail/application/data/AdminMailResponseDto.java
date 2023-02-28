package com.arton.backend.administer.mail.application.data;

import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
@Schema(description = "관리자 페이지 메일 검색 결과")
@ToString
public class AdminMailResponseDto {
    @Schema(description = "ID")
    private Long id;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "메일수신")
    private String termsAgree;
    @Schema(description = "가입일")
    private String createdDate;

    @Builder
    public AdminMailResponseDto(Long id, String nickname, String email, String termsAgree, String createdDate) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.termsAgree = termsAgree;
        this.createdDate = createdDate;
    }

    public static AdminMailResponseDto toResultFromDocument(UserDocument document) {
        return AdminMailResponseDto.builder()
                .id(document.getId())
                .nickname(document.getNickname())
                .email(document.getEmail())
                .termsAgree(document.getTermsAgree())
                .createdDate(isEmpty(document.getCreatedDate()) ? null : document.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}
