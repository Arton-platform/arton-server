package com.arton.backend.search.application.port.out;

import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;

import java.util.Optional;

public interface PerformanceDocumentPort {
    Optional<PerformanceDocument> findById(Long id);
}
