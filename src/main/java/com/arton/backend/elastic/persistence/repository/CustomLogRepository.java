package com.arton.backend.elastic.persistence.repository;

import com.arton.backend.elastic.persistence.document.AccessLogDocument;

import java.util.List;

public interface CustomLogRepository {
    List<AccessLogDocument> getRecentTop10Keywords();
}
