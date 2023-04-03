package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.search.application.data.SearchPageDto;
import com.arton.backend.search.application.data.SearchPageDtoV2;

public interface CustomLogRepository {
    SearchPageDto getRecentTop10Keywords();
    SearchPageDtoV2 getAdvancedTop10Keywords();
    void deleteKeyword(String keyword);
}
