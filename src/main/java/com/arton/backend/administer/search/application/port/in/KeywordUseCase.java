package com.arton.backend.administer.search.application.port.in;

import com.arton.backend.administer.search.application.port.data.AdminKeywordDto;

import java.util.List;

public interface KeywordUseCase {
    List<AdminKeywordDto> getAdminKeywordList();
}
