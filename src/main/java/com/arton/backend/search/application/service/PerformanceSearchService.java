package com.arton.backend.search.application.service;

import com.arton.backend.administer.performance.application.data.PerformanceAdminSearchDto;
import com.arton.backend.infra.jwt.TokenProvider;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.performance.applicaiton.port.out.PerformanceRepositoryPort;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.search.application.data.RecentKeywordResponse;
import com.arton.backend.search.application.data.SearchResultDto;
import com.arton.backend.search.application.port.in.PerformanceSearchUseCase;
import com.arton.backend.search.application.port.in.RecentKeywordDeleteUseCase;
import com.arton.backend.search.application.port.in.RecentKeywordGetUseCase;
import com.arton.backend.search.application.port.in.RecentKeywordSaveUseCase;
import com.arton.backend.search.application.port.out.PerformanceDocuemntSavePort;
import com.arton.backend.search.application.port.out.PerformanceDocumentSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceSearchService implements PerformanceSearchUseCase, RecentKeywordGetUseCase, RecentKeywordSaveUseCase, RecentKeywordDeleteUseCase {
    private final PerformanceRepositoryPort performanceRepositoryPort;
    private final PerformanceDocuemntSavePort performanceDocuemntSavePort;
    private final PerformanceDocumentSearchPort performanceSearchRepository;
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;
    @Value("${search.prefix}")
    private String searchPrefix;

    public void saveAllDocuments() {
        List<Performance> performances = performanceRepositoryPort.findAll();
        performanceDocuemntSavePort.saveAll(performances);
    }

    @Override
    public Page<SearchResultDto> searchByTitle(String title, String sort, Pageable pageable) {
        return performanceSearchRepository.findByTitle(title, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> searchByPlace(String place, String sort, Pageable pageable) {
        // DTO 변환
        return performanceSearchRepository.findByPlace(place, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> searchByPerformanceType(String type, String sort, Pageable pageable) {
        return performanceSearchRepository.findByPerformanceType(type, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    // 유저의 검색 결과 redis에 저장해서 최근 검색어 보여주자
    @Override
    public List<SearchResultDto> searchAll(String type, String sort, Pageable pageable) {
        return performanceSearchRepository.findByKeyword(type, sort, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent())).getContent();
    }

    @Override
    public Page<SearchResultDto> searchInAdmin(PerformanceAdminSearchDto searchDto, Pageable pageable) {
        return performanceSearchRepository.findByDtoInAdmin(searchDto, pageable).map(search -> SearchResultDto.toResultFromDocument(search.getContent()));
    }

    @Override
    public Page<SearchResultDto> findAll(Pageable pageable) {
        return performanceSearchRepository.findAll(pageable).map(SearchResultDto::toResultFromDocument);
    }

    @Override
    public RecentKeywordResponse getUserKeywordHistory(HttpServletRequest request) {
        Authentication authentication = getAuthentication(request);
        String key = searchPrefix + authentication.getName();
        ListOperations listOperations = redisTemplate.opsForList();
        List<String> searchHistories = listOperations.range(key, 0, listOperations.size(key));
        return RecentKeywordResponse.builder().searchHistories(searchHistories).build();
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String accessToken = Optional.ofNullable(tokenProvider.parseBearerToken(request)).orElseThrow(() -> new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID));
        // token 검증
        if (!tokenProvider.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID);
        }
        // get user id
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        return authentication;
    }

    @Override
    public void save(HttpServletRequest request, String keyword) {
        Authentication authentication = getAuthentication(request);
        ListOperations listOperations = redisTemplate.opsForList();
        String key = searchPrefix + authentication.getName();
        // linked list라 다 돌아야함.
        for (Object obj : listOperations.range(key, 0, listOperations.size(key))) {
            String previousKeyword = (String) obj;
            if (previousKeyword.equals(keyword)) // 중복 단어 저장 안함
                return;
        }
        if (listOperations.size(key)<5){
            listOperations.rightPush(key, keyword);
        }else if(listOperations.size(key) == 5) {
            listOperations.leftPop(key);
            listOperations.rightPush(key, keyword);
        }
    }

    @Override
    public void deleteAll(Long userId) {
        String key = searchPrefix + userId;
        redisTemplate.delete(key);
    }

    @Override
    public void deleteAll(HttpServletRequest request) {
        Authentication authentication = getAuthentication(request);
        String key = searchPrefix + authentication.getName();
        redisTemplate.delete(key);
    }

    @Override
    public void deleteOneKeyword(Long userId, String keyword) {
        ListOperations listOperations = redisTemplate.opsForList();
        // 중복이 없으므로 count 1
        listOperations.remove(searchPrefix + userId, 1, keyword);
    }

    @Override
    public void deleteOneKeyword(HttpServletRequest request, String keyword) {
        Authentication authentication = getAuthentication(request);
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.remove(searchPrefix + authentication.getName(), 1, keyword);
    }
}
