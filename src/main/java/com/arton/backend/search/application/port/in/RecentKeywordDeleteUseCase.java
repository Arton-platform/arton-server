package com.arton.backend.search.application.port.in;

import javax.servlet.http.HttpServletRequest;

/**
 * 유저 탈퇴시 검색 기록을 삭제합니다.
 */
public interface RecentKeywordDeleteUseCase {
    void deleteAll(Long userId);
    void deleteAll(HttpServletRequest request);
    void deleteOneKeyword(Long userId, String keyword);
    void deleteOneKeyword(HttpServletRequest request, String keyword);
}
