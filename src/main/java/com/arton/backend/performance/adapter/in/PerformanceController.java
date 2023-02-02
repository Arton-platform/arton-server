package com.arton.backend.performance.adapter.in;

import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import com.arton.backend.performance.domain.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceUseCase performanceUseCase;

    @GetMapping("/list")
    public ResponseEntity<List<PerformanceInterestDto>> getPerformanceList() {
        List<PerformanceInterestDto> allPerformances = performanceUseCase.getZzimList();
        return ResponseEntity.ok(allPerformances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Performance>> getOne(@PathVariable("id") Long id){
        ResponseData response = new ResponseData(
                "SUCCESS"
                , HttpStatus.OK.value()
                , performanceUseCase.getOne(id)
        );
        return ResponseEntity.ok().body(response);
    }
}
