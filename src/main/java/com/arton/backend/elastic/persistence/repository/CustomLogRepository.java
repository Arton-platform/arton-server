package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.application.data.RealTimeKeywordDto;
import com.arton.backend.elastic.persistence.document.AccessLogDocument;

import java.util.List;

public interface CustomLogRepository {
    List<RealTimeKeywordDto> getRecentTop10Keywords();
}
