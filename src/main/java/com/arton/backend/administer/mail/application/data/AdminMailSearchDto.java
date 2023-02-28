package com.arton.backend.administer.mail.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Schema(description = "관리자 페이지 메일 발송 검색 조건")
@ToString
public class AdminMailSearchDto {
    @Schema(description = "이메일/이름/휴대폰 검색어")
    private String keyword;
    @Schema(description = "수신동의")
    private String termsAgree;
    @Schema(description = "가입일 지정 시작일")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gteDate;
    @Schema(description = "가입일 지정 마지막일")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ltDate;
}
