package com.arton.backend.performance.adapter.in;

import com.arton.backend.elastic.persistence.repository.CustomLogRepositoryImpl;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.performance.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSearchUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.domain.Performance;
import lombok.RequiredArgsConstructor;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
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
    private CustomLogRepositoryImpl customLogRepository;
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
    public ResponseEntity<ResponseData<List<PerformanceDocument>>> searchByPlace(HttpServletRequest request, @RequestParam(name = "query") String place) {
        List<PerformanceDocument> documents = performanceSearchService.searchByPlace(place);
        // for elastic search log
        MDC.put("requestURI", request.getRequestURI());
        MDC.put("keyword", place);
        MDC.remove("requestURI");
        MDC.remove("keyword");
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/type")
    public ResponseEntity<ResponseData<List<PerformanceDocument>>> searchByPerformanceType(HttpServletRequest request, @RequestParam(name = "query", required = true) String performanceType) {
        List<PerformanceDocument> documents = performanceSearchService.searchByPerformanceType(performanceType);
        MDC.put("requestURI", request.getRequestURI());
        MDC.put("keyword", performanceType);
        MDC.remove("requestURI");
        MDC.remove("keyword");
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/title")
    public ResponseEntity<ResponseData<List<PerformanceDocument>>> search(HttpServletRequest request, @RequestParam(name = "query", required = true) String query) {
        List<PerformanceDocument> documents = performanceSearchService.searchByTitle(query);
        MDC.put("requestURI", request.getRequestURI());
        MDC.put("keyword", query);
        MDC.remove("requestURI");
        MDC.remove("keyword");
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

}
