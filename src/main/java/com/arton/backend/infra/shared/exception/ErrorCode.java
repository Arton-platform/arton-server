package com.arton.backend.infra.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_IS_EXIST(409, "DUP_ERR_400", "해당 이메일은 사용중입니다."),
    PARAMETER_NOT_VALID(400, "PARAMETER_ERR_400", "유효하지 않은 입력값입니다."),
    USER_NOT_FOUND(404, "COMMON-ERR-404", "사용자를 찾을 수 없습니다"),
    PASSWORD_NOT_MATCH(400, "NOT_MATCH_400", "패스워드가 일치하지 않습니다."),
    LOGIN_INFO_NOT_MATCHED(400, "NOT_MATCH_401", "아이디 또는 패스워드가 틀립니다."),
    USER_NOT_AUTHORITY(403, "COMMON-ERR-403", "권한이 없습니다."),
    TOKEN_INVALID(401, "TOKEN_ERR", "유효하지 않은 토큰입니다."),
    MAIL_SEND_ERROR(500, "MAIL_SEND_ERROR", "메일 발송에 실패하였습니다."),
    KAKAO_SIMPLE_LOGIN_ERROR(500, "KAKAO_SIMPLE_LOGIN_ERROR", "카카오 간편 로그인에 실패하였습니다."),
    NAVER_SIMPLE_LOGIN_ERROR(500, "NAVER_SIMPLE_LOGIN_ERROR", "네이버 간편 로그인에 실패하였습니다."),
    INVALID_URI_REQUEST(400, "INVALID_URI_REQUEST", "잘못된 URI 요청입니다."),
    SELECT_ERROR(404, "SELECT_ERROR", "조회에 실패 했습니다.");
    private int status;
    private String errorCode;
    private String message;
}
