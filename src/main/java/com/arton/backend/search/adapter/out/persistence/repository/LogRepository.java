package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.search.adapter.out.persistence.document.AccessLogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogRepository extends ElasticsearchRepository<AccessLogDocument, Long>, CustomLogRepository {

}
