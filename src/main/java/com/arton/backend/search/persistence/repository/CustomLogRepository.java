package com.arton.backend.search.persistence.repository;

import com.arton.backend.search.application.data.SearchPageDto;

public interface CustomLogRepository {
    SearchPageDto getRecentTop10Keywords();
}
