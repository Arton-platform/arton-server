package com.arton.backend.search.persistence.repository;

import com.arton.backend.search.application.data.RealTimeKeywordDto;

import java.util.List;

public interface CustomLogRepository {
    List<RealTimeKeywordDto> getRecentTop10Keywords();
}
