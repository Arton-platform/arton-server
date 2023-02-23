package com.arton.backend.administer.search.application.service;

import com.arton.backend.administer.search.application.port.in.KeywordDeleteUseCase;
import com.arton.backend.search.application.port.out.LogDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService implements KeywordDeleteUseCase {
    private final LogDeletePort logDeletePort;
    @Override
    public void deleteKeyword(String keyword) {

    }
}
