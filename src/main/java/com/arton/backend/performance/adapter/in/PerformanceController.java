package com.arton.backend.performance.adapter.in;

import com.arton.backend.performance.applicaiton.port.in.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceUseCase performanceUseCase;

    @GetMapping
    public ResponseEntity<List<PerformanceInterestDto>> getPerformanceList() {
        List<PerformanceInterestDto> allPerformances = performanceUseCase.getZzimList();
        return ResponseEntity.ok(allPerformances);
    }

}
