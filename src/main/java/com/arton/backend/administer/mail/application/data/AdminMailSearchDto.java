package com.arton.backend.administer.mail.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@Schema(description = "관리자 페이지 메일 발송 검색 조건")
@ToString
public class AdminMailSearchDto {
    private String nickname;
    private String email;
    private String termsAgree;
}
