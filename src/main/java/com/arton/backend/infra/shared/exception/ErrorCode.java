package com.arton.backend.infra.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_IS_EXIST(409, "DUP_ERR_409", "해당 이메일은 사용중입니다."),
    ZZIM_IS_EXIST(409, "DUP_ERR_409", "이미 찜하고 있습니다."),
    ZZIM_NOT_FOUND(404, "COMMON-ERR-404", "찜 목록에 존재하지 않습니다!"),
    REVIEW_NOT_FOUND(404, "COMMON-ERR-404", "유저의 리뷰가 존재하지 않습니다!"),
    REVIEW_PERFORMANCE_NOT_MATCHED(400, "REVIEW_PERFORMANCE_NOT_MATCHED", "댓글을 등록하려고 하는 공연과 요청 공연 ID가 매치하지 않습니다!"),
    FOLLOWING_IS_EXIST(409, "DUP_ERR_409", "이미 팔로우중인 대상입니다."),
    FOLLOWING_INVALID(400, "FOLLOWING_INVALID", "잘못된 팔로우 요청입니다."),
    UNFOLLOWING_INVALID(400, "UNFOLLOWING_INVALID", "잘못된 언팔로우 요청입니다."),
    UPLOAD_COUNT_LIMIT(400, "UPLOAD_COUNT_LIMIT", "등록 가능한 이미지 개수를 초과하였습니다. 이미지는 최대 6장 등록가능합니다."),
    BAD_REQUEST(400, "BAD_REQUEST", "잘못된 요청입니다."),
    FILE_EMPTY(400, "FILE_EMPTY", "빈 파일입니다.."),
    FILE_UPLOAD_FAILED(500, "FILE_UPLOAD_FAILED", "파일 업로드에 실패하였습니다."),
    FILE_COPY_FAILED(500, "FILE_COPY_FAILED", "파일 복사에 실패하였습니다."),
    IMAGE_LOAD_FAILED(500, "IMAGE_LOAD_FAILED", "이미지 로드에 실패하였습니다."),
    PARAMETER_NOT_VALID(400, "PARAMETER_ERR_400", "유효하지 않은 입력값입니다."),
    EXCEL_TYPE_NOT_VALID(400, "EXCEL_ERR_400", "유효하지 엑셀 설정값입니다."),
    EXCEL_ANNOTATION_NOT_FOUND(400, "EXCEL_ERR_400", "엑셀 어노테이션을 찾을 수 없습니다."),
    EXCEL_BORDER_NOT_VALID(400, "EXCEL_ERR_400", "테두리 설정이 유효하지 않습니다."),
    EXCEL_FIELD_NOT_FOUND(400, "EXCEL_ERR_400", "해당 필드값을 찾을 수 않습니다."),
    EXCEL_CELL_STYLE_INVALID(400, "EXCEL_ERR_400", "셀 스타일이 유효하지 않습니다."),
    EXCEL_ENUM_NULL(400, "EXCEL_ERR_400", "엑셀 ENUM 값이 NULL입니다."),
    EXCEL_INTERNAL_ERROR(500, "EXCEL_ERR_500", "엑셀 작업중 오류가 발생했습니다."),
    AUTO_MAIL_ERROR(500, "AUTO_MAIL_ERR_500", "자동 메일 발송중 에러가 발생했습니다."),
    USER_NOT_FOUND(404, "COMMON-ERR-404", "사용자를 찾을 수 없습니다"),
    MAIL_NOT_FOUND(404, "COMMON-ERR-404", "메일을 찾을 수 없습니다"),
    TERMS_NOT_FOUND(404, "COMMON-ERR-404", "약관을 찾을 수 없습니다"),
    PERFORMANCE_NOT_FOUND(404, "COMMON-ERR-404", "공연을 찾을 수 없습니다"),
    PERFORMANCE_DOCUMENT_NOT_FOUND(404, "COMMON-ERR-404", "공연문서를 찾을 수 없습니다"),
    ARTIST_NOT_FOUND(404, "COMMON-ERR-404", "아티스트를 찾을 수 없습니다"),
    PASSWORD_NOT_MATCH(401, "NOT_MATCH_401", "패스워드가 일치하지 않습니다."),
    LOGIN_INFO_NOT_MATCHED(401, "NOT_MATCH_401", "아이디 또는 패스워드가 틀립니다."),
    USER_NOT_AUTHORITY(403, "COMMON-ERR-403", "권한이 없습니다."),
    TOKEN_INVALID(401, "TOKEN_ERR", "유효하지 않은 토큰입니다."),
    MAIL_SEND_ERROR(500, "MAIL_SEND_ERROR", "메일 발송에 실패하였습니다."),
    REAL_TIME_SEARCH_ERROR(500, "REAL_TIME_SEARCH_ERROR", "실시간 검색어 집계에 실패하였습니다."),
    KAKAO_SIMPLE_LOGIN_ERROR(500, "KAKAO_SIMPLE_LOGIN_ERROR", "카카오 간편 로그인에 실패하였습니다."),
    NAVER_SIMPLE_LOGIN_ERROR(500, "NAVER_SIMPLE_LOGIN_ERROR", "네이버 간편 로그인에 실패하였습니다."),
    APPLE_SIMPLE_LOGIN_ERROR(500, "APPLE_SIMPLE_LOGIN_ERROR", "애플 간편 로그인에 실패하였습니다."),
    GOOGLE_SIMPLE_LOGIN_ERROR(500, "GOOGLE_SIMPLE_LOGIN_ERROR", "구글 간편 로그인에 실패하였습니다."),
    INVALID_URI_REQUEST(400, "INVALID_URI_REQUEST", "잘못된 URI 요청입니다."),
    INVALID_KEYSPEC_REQUEST(400, "INVALID_KEYSPEC_REQUEST", "잘못된 키스펙 요청입니다."),
    INVALID_ALGORITHM_REQUEST(400, "INVALID_ALGORITHM_REQUEST", "잘못된 알고리즘 요청입니다."),
    FORBIDDEN_REQUEST(403, "INVALID_REQUEST", "잘못된 요청입니다."),
    JSON_PROCESSING_ERROR(600, "JSON_PROCESSING_ERROR", "JSON 변환 오류 입니다."),
    IO_EXCEPTION(600, "IO_EXCEPTION", "입출력 오류 입니다."),
    UNSUPPORTED_MEDIA_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "UNSUPPORTED_ERROR", "지원하지 않는 타입입니다."),
    HIT_INVALID_ERROR(400, "HIT_INVALID_ERROR", "현재 좋아요는 0개 입니다. 0개 미만으로 변경할 수 없습니다."),
    SELECT_ERROR(404, "SELECT_ERROR", "조회에 실패 했습니다."),
    EXCEED_LIMITED_SIZE_ERROR(400, "EXCEED_LIMITED_SIZE_ERROR", "파일이 제한 용량을 초과합니다."),
    REGIST_ERROR(500, "REGIST_ERROR", "저장에 문제가 발생했습니다."),
    DELETE_ERROR(500, "DELETE_ERROR", "삭제에 문제가 발생했습니다."),
    INTERNAL_SERVER_ERROR(500, "SERVER_ERROR", "서버에 문제가 발생했습니다.");
    ;

    private int status;
    private String errorCode;
    private String message;
}
