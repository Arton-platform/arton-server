package com.arton.backend.administer.search.application.service;

import com.arton.backend.administer.search.application.port.data.AdminKeywordDto;
import com.arton.backend.administer.search.application.port.in.KeywordDeleteUseCase;
import com.arton.backend.administer.search.application.port.in.KeywordUseCase;
import com.arton.backend.search.application.data.SearchPageDtoV2;
import com.arton.backend.search.application.port.out.LogDeletePort;
import com.arton.backend.search.application.port.out.LogPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService implements KeywordDeleteUseCase, KeywordUseCase {
    private final LogDeletePort logDeletePort;
    private final LogPort logPort;
    @Override
    public void deleteKeyword(String keyword) {
        logDeletePort.deleteKeyword(keyword);
    }

    @Override
    public List<AdminKeywordDto> getAdminKeywordList() {
        SearchPageDtoV2 advancedTop10Keywords = logPort.getAdvancedTop10Keywords();
        if (!ObjectUtils.isEmpty(advancedTop10Keywords)) {
            if (!ObjectUtils.isEmpty(advancedTop10Keywords.getKeywords())) {
                return advancedTop10Keywords.getKeywords().stream().map(AdminKeywordDto::toAdminDto).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
