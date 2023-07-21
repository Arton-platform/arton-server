package com.arton.backend.zzim.adapter.in;

import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import com.arton.backend.infra.shared.exception.ErrorResponse;
import com.arton.backend.zzim.application.data.ZzimCreateDto;
import com.arton.backend.zzim.application.data.ZzimDeleteDto;
import com.arton.backend.zzim.application.port.in.ZzimCreateUseCase;
import com.arton.backend.zzim.application.port.in.ZzimDeleteUseCase;
import com.arton.backend.zzim.application.port.in.ZzimUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/zzim")
@RequiredArgsConstructor
public class ZzimController {
    private final ZzimUseCase zzimService;
    private final ZzimCreateUseCase zzimCreateUseCase;
    private final ZzimDeleteUseCase zzimDeleteUseCase;

    /**
     * Android 에서는 DELETE와 함께 본문을 전송할 수 없음
     * Tomcat, Weblogic은 Payload가 있는 DELETE 요청을 거부
     * OpenAPI 사양에서 본문이 있는 DELETE 요청 지원을 중단
     * Google Cloud HTTPS LoadBalancer가 상태 값 400을 반환하는 DELETE 요청을 거부
     * Sahi Pro는 DELETE 요청에 제공된 Body를 무시
     * 일부 버전의 Tomcat 및 Jetty는 엔터티 본문을 무시
     * 네이버나, 토스의 경우 POST로 선택 삭제를 구현했다는 글이 있어 POST로 구현을 하도록 하겠습니다.
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "찜취소", description = "산텍힌 리스트에 대해 찜을 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "찜 취소 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))})
    @PostMapping("/cancel")
    public ResponseEntity<CommonResponse> deleteFavorites(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ZzimDeleteDto deleteDto) {
        long userId = Long.parseLong(userDetails.getUsername());
        zzimService.deleteUsersFavorite(userId, deleteDto);
        CommonResponse response = CommonResponse.builder().message("").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }


    /**
     * 유저를 기준으로 Artist, Performance 의 찜 목록을 호출한다.
     * @param userDetails
     * @return HashMap<String, Object>
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "찜 가져오기", description = "유저의 찜 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "찜 리스트 가져오기 성공",
                    content = @Content(schema = @Schema(implementation = Map.class)))})
    @GetMapping("/list")
    public ResponseEntity<ResponseData<Map>> zzimList(@AuthenticationPrincipal UserDetails userDetails){
        long userId = Long.parseLong(userDetails.getUsername());
        HashMap<String,Object> zzimMap = new HashMap<>();
        zzimMap.put("performance", zzimService.performanceList(userId));
        zzimMap.put("artist", zzimService.artistList(userId));

        ResponseData response = new ResponseData(
                "SUCCESS",
                HttpStatus.OK.value(),
                zzimMap
        );
        return ResponseEntity.ok(response);
    }

    /**
     * 해당 공연을 찜 한다
     * 하트 표시
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "공연 좋아요 하기", description = "해당 공연을 좋아요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공연 좋아요 성공",
                    content = @Content(schema = @Schema(implementation = String.class))),
    @ApiResponse(responseCode = "404", description = "존재하지 않는 공연",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 좋아요한 공연",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/performance")
    public ResponseEntity zzimPerformance(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ZzimCreateDto zzimCreateDto){
        long userId = Long.parseLong(userDetails.getUsername());
        zzimCreateUseCase.createPerformanceZzim(userId, zzimCreateDto);
        return ResponseEntity.ok(CommonResponse.builder().message("공연 좋아요 성공").status(HttpStatus.OK.value()).build());
    }

    /**
     * 찜한 공연을 단건 취소한다.
     * 하트 감소
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "찜한 공연 취소", description = "해당 공연 좋아요 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공연 좋아요 취소 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공연",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "좋아요 공연이 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/performance/{performanceId}")
    public ResponseEntity unZzimPerformance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "performanceId") Long performanceId){
        long userId = Long.parseLong(userDetails.getUsername());
        zzimDeleteUseCase.deletePerformanceZzim(userId, performanceId);
        CommonResponse response = CommonResponse.builder().message("").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    /**
     * 해당 아티스트를 찜 한다
     * 하트 표시
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "아티스트 좋아요", description = "해당 아티스트를 좋아요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20", description = "아티스트 좋아요 성공",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 아티스트",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 찜한 아티스트",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/artist")
    public ResponseEntity zzimArtist(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ZzimCreateDto zzimCreateDto){
        long userId = Long.parseLong(userDetails.getUsername());
        zzimCreateUseCase.createArtistZzim(userId, zzimCreateDto);
        return ResponseEntity.ok(CommonResponse.builder().message("아티스트 좋아요 성공").status(HttpStatus.OK.value()).build());
    }

    /**
     * 찜한 아티스를 단건 취소한다.
     */
    @Parameter(name = "userDetails", hidden = true)
    @Operation(summary = "좋아요 아티스트 취소", description = "해당 아티스트 좋아요 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아티스트 좋아요 취소 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "찜한 아티스트가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/artist/{artistId}")
    public ResponseEntity<CommonResponse> unZzimArtist(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "artistId") Long artistId){
        long userId = Long.parseLong(userDetails.getUsername());
        zzimDeleteUseCase.deleteArtistZzim(userId, artistId);
        CommonResponse response = CommonResponse.builder().message("").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

}
