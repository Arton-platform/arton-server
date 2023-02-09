package com.arton.backend.performance.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSearchUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.domain.Performance;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.application.data.SearchPageDto;
import com.arton.backend.search.application.data.SearchResultDto;
import com.arton.backend.search.adapter.out.persistence.repository.LogRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceUseCase performanceService;
    private final PerformanceSearchUseCase performanceSearchService;
    private final LogRepository logRepository;
    private final static Logger log = LoggerFactory.getLogger("LOGSTASH");

    @GetMapping("/list")
    public ResponseEntity<List<PerformanceInterestDto>> getPerformanceList() {
        List<PerformanceInterestDto> allPerformances = performanceService.getZzimList();
        return ResponseEntity.ok(allPerformances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Performance>> getOne(@PathVariable("id") Long id){
        ResponseData response = new ResponseData(
                "SUCCESS"
                , HttpStatus.OK.value()
                , performanceService.getOne(id)
        );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/documents")
    public ResponseEntity<CommonResponse> saveDocuments() {
        performanceSearchService.saveAllDocuments();
        return ResponseEntity.ok(CommonResponse.builder().message("ES에 성공적으로 저장하였습니다.").status(HttpStatus.OK.value()).build());
    }

    @GetMapping("/search/place")
    public ResponseEntity<ResponseData<Page<SearchResultDto>>> searchByPlace(HttpServletRequest request, @RequestParam(name = "query") String place, @RequestParam(name = "sort", required = false) String sort, Pageable pageable) {
        Page<SearchResultDto> documents = performanceSearchService.searchByPlace(place, sort, pageable);
        MDC.put("keyword", place);
        log.info("requestURI={}, keyword={}", StructuredArguments.value("requestURI", request.getRequestURI()), StructuredArguments.value("keyword", place));
        MDC.remove("keyword");
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/type")
    public ResponseEntity<ResponseData<List<SearchResultDto>>> searchByPerformanceType(HttpServletRequest request, @RequestParam(name = "query", required = true) String performanceType, @RequestParam(name = "sort", required = false) String sort) {
        List<SearchResultDto> documents = performanceSearchService.searchByPerformanceType(performanceType, sort);
        MDC.put("keyword", performanceType);
        log.info("requestURI={}, keyword={}", StructuredArguments.value("requestURI", request.getRequestURI()), StructuredArguments.value("keyword", performanceType));
        MDC.remove("keyword");
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/title")
    public ResponseEntity<ResponseData<List<SearchResultDto>>> search(HttpServletRequest request, @RequestParam(name = "query", required = true) String query, @RequestParam(name = "sort", required = false) String sort) {
        List<SearchResultDto> documents = performanceSearchService.searchByTitle(query, sort);
        MDC.put("keyword", query);
        log.info("requestURI={}, keyword={}", StructuredArguments.value("requestURI", request.getRequestURI()), StructuredArguments.value("keyword", query));
        MDC.remove("keyword");
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData<SearchPageDto>> searchLog() {
        SearchPageDto searchPage = logRepository.getRecentTop10Keywords();
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), searchPage);
        return ResponseEntity.ok().body(response);
    }

}
