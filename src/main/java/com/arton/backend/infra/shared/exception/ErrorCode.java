package com.arton.backend.infra.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_IS_EXIST(409, "DUP_ERR_400", "해당 이메일은 사용중입니다."),
    FOLLOWING_IS_EXIST(409, "DUP_ERR_400", "이미 팔로우중인 대상입니다."),
    FOLLOWING_INVALID(400, "FOLLOWING_INVALID", "잘못된 팔로우 요청입니다."),
    UNFOLLOWING_INVALID(400, "UNFOLLOWING_INVALID", "잘못된 언팔로우 요청입니다."),
    FILE_EMPTY(400, "FILE_EMPTY", "빈 파일입니다.."),
    FILE_UPLOAD_FAILED(500, "FILE_UPLOAD_FAILED", "파일 업로드에 실패하였습니다."),
    IMAGE_LOAD_FAILED(500, "IMAGE_LOAD_FAILED", "이미지 로드에 실패하였습니다."),
    PARAMETER_NOT_VALID(400, "PARAMETER_ERR_400", "유효하지 않은 입력값입니다."),
    USER_NOT_FOUND(404, "COMMON-ERR-404", "사용자를 찾을 수 없습니다"),
    PERFORMANCE_NOT_FOUND(404, "COMMON-ERR-404", "공연을 찾을 수 없습니다"),
    PASSWORD_NOT_MATCH(401, "NOT_MATCH_401", "패스워드가 일치하지 않습니다."),
    LOGIN_INFO_NOT_MATCHED(401, "NOT_MATCH_401", "아이디 또는 패스워드가 틀립니다."),
    USER_NOT_AUTHORITY(403, "COMMON-ERR-403", "권한이 없습니다."),
    TOKEN_INVALID(401, "TOKEN_ERR", "유효하지 않은 토큰입니다."),
    MAIL_SEND_ERROR(500, "MAIL_SEND_ERROR", "메일 발송에 실패하였습니다."),
    REAL_TIME_SEARCH_ERROR(500, "REAL_TIME_SEARCH_ERROR", "실시간 검색어 집계에 실패하였습니다."),
    KAKAO_SIMPLE_LOGIN_ERROR(500, "KAKAO_SIMPLE_LOGIN_ERROR", "카카오 간편 로그인에 실패하였습니다."),
    NAVER_SIMPLE_LOGIN_ERROR(500, "NAVER_SIMPLE_LOGIN_ERROR", "네이버 간편 로그인에 실패하였습니다."),
    INVALID_URI_REQUEST(400, "INVALID_URI_REQUEST", "잘못된 URI 요청입니다."),
    FORBIDDEN_REQUEST(403, "INVALID_REQUEST", "잘못된 요청입니다."),
    JSON_PROCESSING_ERROR(600, "JSON_PROCESSING_ERROR", "JSON 변환 오류 입니다."),
    IO_EXCEPTION(600, "IO_EXCEPTION", "입출력 오류 입니다."),
    UNSUPPORTED_MEDIA_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "UNSUPPORTED_ERROR", "지원하지 않는 타입입니다."),
    HIT_INVALID_ERROR(400, "HIT_INVALID_ERROR", "현재 좋아요는 0개 입니다. 0개 미만으로 변경할 수 없습니다."),
    SELECT_ERROR(404, "SELECT_ERROR", "조회에 실패 했습니다."),
    REGIST_ERROR(500, "REGIST_ERROR", "저장에 문제가 발생했습니다."),
    DELETE_ERROR(500, "DELETE_ERROR", "삭제에 문제가 발생했습니다.");
    ;

    private int status;
    private String errorCode;
    private String message;
}
