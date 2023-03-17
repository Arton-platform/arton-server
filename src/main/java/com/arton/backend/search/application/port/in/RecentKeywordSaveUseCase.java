package com.arton.backend.search.application.port.in;

import javax.servlet.http.HttpServletRequest;

public interface RecentKeywordSaveUseCase {
    void save(HttpServletRequest request, String keyword);
}
