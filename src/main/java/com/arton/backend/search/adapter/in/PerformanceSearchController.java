package com.arton.backend.search.adapter.in;

import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.search.adapter.out.persistence.repository.LogRepository;
import com.arton.backend.search.application.data.RecentKeywordResponse;
import com.arton.backend.search.application.data.SearchPageDto;
import com.arton.backend.search.application.data.SearchPageDtoV2;
import com.arton.backend.search.application.data.SearchResultDto;
import com.arton.backend.search.application.port.in.PerformanceSearchUseCase;
import com.arton.backend.search.application.port.in.RecentKeywordGetUseCase;
import com.arton.backend.search.application.port.in.RecentKeywordSaveUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.argument.StructuredArguments;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "PERFORMANCE", description = "공연 검색 API")
@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceSearchController {
    private final PerformanceSearchUseCase performanceSearchService;
    private final RecentKeywordSaveUseCase recentKeywordSaveUseCase;
    private final RecentKeywordGetUseCase recentKeywordGetUseCase;
    private final LogRepository logRepository;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");

    @Operation(summary = "검색하기", description = "검색을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = SearchResultDto.class)))),
            @ApiResponse(responseCode = "401", description = "토큰에러",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/search/document")
    public ResponseEntity<ResponseData<Page<SearchResultDto>>> searchByMultiMatch(HttpServletRequest request, @RequestParam(name = "query", required = true) String query, @RequestParam(name = "sort", required = false) String sort, @PageableDefault(size = 10) Pageable pageable) {
        recentKeywordSaveUseCase.save(request, query);
        Page<SearchResultDto> documents = performanceSearchService.searchAll(query, sort, pageable);
        MDC.put("keyword", query);
        log.info("requestURI={}, keyword={}", StructuredArguments.value("requestURI", request.getRequestURI()), StructuredArguments.value("keyword", query));
        MDC.remove("keyword");
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "실시간 검색어 화면", description = "실시간 검색어 순위를 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공",
                    content = @Content(schema = @Schema(implementation = SearchPageDto.class))),
    @ApiResponse(responseCode = "500", description = "실시간 검색어 집계 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/search")
    public ResponseEntity<ResponseData<SearchPageDto>> searchLog() {
        SearchPageDto searchPage = logRepository.getRecentTop10Keywords();
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), searchPage);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "실시간 검색어 Advanced", description = "실시간 검색어 순위를 보여줍니다. Advanced 버전")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검색 성공",
                    content = @Content(schema = @Schema(implementation = SearchPageDto.class))),
            @ApiResponse(responseCode = "500", description = "실시간 검색어 집계 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/search/v2")
    public ResponseEntity<ResponseData<SearchPageDtoV2>> searchLogV2() {
        SearchPageDtoV2 searchPage = logRepository.getAdvancedTop10Keywords();
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), searchPage);
        return ResponseEntity.ok().body(response);
    }

    @Parameter(name = "request", hidden = true)
    @Operation(summary = "유저의 검색 히스토리", description = "유저의 검색 기록을 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "데이터 가져오기 성공",
                    content = @Content(schema = @Schema(implementation = RecentKeywordResponse.class))),
            @ApiResponse(responseCode = "401", description = "토큰에러",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/search/history")
    public ResponseEntity<ResponseData<RecentKeywordResponse>> recentKeywordHistories(HttpServletRequest request) {
        RecentKeywordResponse recentKeywordResponse = recentKeywordGetUseCase.getUserKeywordHistory(request);
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), recentKeywordResponse);
        return ResponseEntity.ok().body(response);
    }



}
