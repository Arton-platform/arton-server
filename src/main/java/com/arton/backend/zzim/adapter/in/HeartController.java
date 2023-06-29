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
@RequestMapping("/heart")
@RequiredArgsConstructor
public class HeartController {
    private final ZzimCreateUseCase zzimCreateUseCase;
    private final ZzimDeleteUseCase zzimDeleteUseCase;

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
            @ApiResponse(responseCode = "204", description = "공연 좋아요 취소 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공연",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "좋아요 공연이 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/performance/{performanceId}")
    public ResponseEntity unZzimPerformance(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "performanceId") Long performanceId){
        long userId = Long.parseLong(userDetails.getUsername());
        zzimDeleteUseCase.deletePerformanceZzim(userId, performanceId);
        return ResponseEntity.noContent().build();
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
            @ApiResponse(responseCode = "204", description = "아티스트 좋아요 취소 성공"),
            @ApiResponse(responseCode = "404", description = "찜한 아티스트가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/artist/{artistId}")
    public ResponseEntity unZzimArtist(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "artistId") Long artistId){
        long userId = Long.parseLong(userDetails.getUsername());
        zzimDeleteUseCase.deleteArtistZzim(userId, artistId);
        return ResponseEntity.noContent().build();
    }

}
