package com.arton.backend.performance.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.performance.applicaiton.data.*;
import com.arton.backend.performance.applicaiton.port.in.CrawlerPerformanceSaveUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceSaveUseCase;
import com.arton.backend.performance.applicaiton.port.in.PerformanceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Tag(name = "PERFORMANCE", description = "공연 API")
@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceUseCase performanceService;
    private final CrawlerPerformanceSaveUseCase crawlerPerformanceSaveUseCase;


    @Operation(summary = "회원가입시 찜에 필요한 공연 리스트 불러오기", description = "회원가입시 찜에 필요한 공연 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 가져오기 성공",
                    content = @Content(schema = @Schema(implementation = PerformanceZzimDetailDTO.class)))})
    @GetMapping("/zzim")
    public ResponseEntity<ResponseData<PerformanceZzimDetailDTO>> getPerformanceZzimList(@PageableDefault(size = 9)Pageable pageable) {
        PerformanceZzimDetailDTO allPerformances = performanceService.getZzimListV2(pageable);
        ResponseData<PerformanceZzimDetailDTO> response = new ResponseData<>("OK", 200, allPerformances);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원가입시 찜에 필요한 공연 리스트 불러오기", description = "회원가입시 찜에 필요한 공연 리스트를 가져옵니다.(이미지 링크, 가격 정보 전부 가져옴)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 가져오기 성공",
                    content = @Content(schema = @Schema(implementation = PerformanceZzimDetailDTOV2.class)))})
    @GetMapping("/zzim/v2")
    public ResponseEntity<ResponseData<PerformanceZzimDetailDTOV2>> getPerformanceZzimListV2(@PageableDefault(size = 9)Pageable pageable) {
        PerformanceZzimDetailDTOV2 allPerformances = performanceService.getZzimListAllRelatedInfos(pageable);
        ResponseData<PerformanceZzimDetailDTOV2> response = new ResponseData<>("OK", 200, allPerformances);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체 공연 리스트 불러오기", description = "공연 리스트를 페이징처리하여 정렬 조건에 따라 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 가져오기 성공",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = CommonPerformanceDto.class))))})
    @GetMapping("/list")
    public ResponseEntity<List<CommonPerformanceDto>> getPerformanceList(@RequestParam(name = "sort", required = false) String sort, @PageableDefault(size = 10)Pageable pageable) {
        List<CommonPerformanceDto> allPerformances = performanceService.getPerformanceBySortAndPage(pageable, sort);
        return ResponseEntity.ok(allPerformances);
    }

    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "특정 공연 상세보기", description = "공연을 상세보기 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상세 데이터 가져오기 성공",
                    content = @Content( schema = @Schema(implementation = PerformanceDetailDtoV3.class))),
            @ApiResponse(responseCode = "404", description = "공연을 찾을 수 없음.",
                    content = @Content( schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<PerformanceDetailDtoV3>> getOne(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id){
        long userId = Long.parseLong(userDetails.getUsername());
        ResponseData response = new ResponseData(
                "SUCCESS"
                , HttpStatus.OK.value()
                , performanceService.getOneWithArtistReviewInfo(userId, id)
        );
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/crawler")
    public ResponseEntity<CommonResponse> addByCrawler(@RequestBody CrawlerPerformanceCreateDTO crawlerPerformanceCreateDTO) {
        crawlerPerformanceSaveUseCase.addByCrawler(crawlerPerformanceCreateDTO);
        CommonResponse response = CommonResponse.builder().message("성공적으로 등록하였습니다").status(200).build();
        return ResponseEntity.ok(response);
    }
}
