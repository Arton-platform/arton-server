package com.arton.backend.search.application.port.out;

import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;

import java.util.List;

public interface PerformanceDocuemntSavePort {
    void saveAll(List<PerformanceDocument> performanceDocuments);
    void save(PerformanceDocument performanceDocument);
}
