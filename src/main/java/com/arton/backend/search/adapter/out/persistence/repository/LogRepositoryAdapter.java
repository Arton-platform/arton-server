package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.search.application.data.SearchPageDto;
import com.arton.backend.search.application.data.SearchPageDtoV2;
import com.arton.backend.search.application.port.out.LogDeletePort;
import com.arton.backend.search.application.port.out.LogPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LogRepositoryAdapter implements LogDeletePort, LogPort {
    private final LogRepository logRepository;
    @Override
    public void deleteKeyword(String keyword) {
        logRepository.deleteKeyword(keyword);
    }

    @Override
    public SearchPageDto getRecentTop10Keywords() {
        return logRepository.getRecentTop10Keywords();
    }

    @Override
    public SearchPageDtoV2 getAdvancedTop10Keywords() {
        return logRepository.getAdvancedTop10Keywords();
    }
}
