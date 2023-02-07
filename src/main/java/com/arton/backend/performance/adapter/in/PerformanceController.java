package com.arton.backend.performance.adapter.in;

import com.arton.backend.elastic.persistence.document.AccessLogDocument;
import com.arton.backend.elastic.persistence.repository.CustomLogRepository;
import com.arton.backend.elastic.persistence.repository.CustomLogRepositoryImpl;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.performance.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.data.PerformanceSearchDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSearchUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.domain.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceUseCase performanceService;
    private final PerformanceSearchUseCase performanceSearchService;
    private CustomLogRepositoryImpl customLogRepository;

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
    public ResponseEntity<ResponseData<List<PerformanceDocument>>> searchByPlace(@RequestParam(name = "query") String place) {
        List<PerformanceDocument> documents = performanceSearchService.searchByPlace(place);
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/type")
    public ResponseEntity<ResponseData<List<PerformanceDocument>>> searchByPerformanceType(@RequestParam(name = "query", required = true) String performanceType) {
        List<PerformanceDocument> documents = performanceSearchService.searchByPerformanceType(performanceType);
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/title")
    public ResponseEntity<ResponseData<List<PerformanceDocument>>> search(@RequestParam(name = "query", required = true) String query) {
        List<PerformanceDocument> documents = performanceSearchService.searchByTitle(query);
        ResponseData response = new ResponseData("SUCCESS", HttpStatus.OK.value(), documents);
        return ResponseEntity.ok().body(response);
    }

}
