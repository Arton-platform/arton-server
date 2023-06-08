package com.arton.backend.search.application.port.out;

import java.util.List;

public interface PerformanceDocumentDeletePort {
    void deleteById(Long id);
    void deleteByIds(List<Long> ids);
}
