package com.arton.backend.search.application.port.in;

import com.arton.backend.search.application.data.RecentKeywordResponse;

import javax.servlet.http.HttpServletRequest;

public interface RecentKeywordGetUseCase {
    RecentKeywordResponse getUserKeywordHistory(HttpServletRequest request);
}
