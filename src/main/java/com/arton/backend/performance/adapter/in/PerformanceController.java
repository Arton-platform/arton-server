package com.arton.backend.performance.adapter.in;

import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailQueryDslDto;
import com.arton.backend.performance.applicaiton.data.PerformanceDetailDto;
import com.arton.backend.performance.applicaiton.data.PerformanceInterestDto;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "PERFORMANCE", description = "공연 API")
@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceUseCase performanceService;

    @Operation(summary = "회원가입시 찜에 필요한 공연 리스트 불러오기", description = "회원가입시 찜에 필요한 공연 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 가져오기 성공",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = PerformanceInterestDto.class))))})
    @GetMapping("/list/zzim")
    public ResponseEntity<List<PerformanceInterestDto>> getPerformanceZzimList() {
        List<PerformanceInterestDto> allPerformances = performanceService.getZzimList();
        return ResponseEntity.ok(allPerformances);
    }

    @Operation(summary = "전체 공연 리스트 불러오기", description = "전체 공연 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 가져오기 성공",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = PerformanceInterestDto.class))))})
    @GetMapping("/list")
    public ResponseEntity<List<PerformanceInterestDto>> getPerformanceList() {
        List<PerformanceInterestDto> allPerformances = performanceService.getZzimList();
        return ResponseEntity.ok(allPerformances);
    }

    @Operation(summary = "특정 공연 상세보기", description = "공연을 상세보기 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세 데이터 가져오기 성공",
                    content = @Content( schema = @Schema(implementation = PerformanceDetailDto.class))),
            @ApiResponse(responseCode = "404", description = "공연을 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = CustomException.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<PerformanceDetailDto>> getOne(@PathVariable("id") Long id){
        ResponseData response = new ResponseData(
                "SUCCESS"
                , HttpStatus.OK.value()
                , performanceService.getOne(id)
        );
        return ResponseEntity.ok().body(response);
    }

}
