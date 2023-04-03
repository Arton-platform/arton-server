package com.arton.backend.artist.adapter.in;

import com.arton.backend.artist.application.data.ArtistInterestDto;
import com.arton.backend.artist.application.port.in.ArtistUseCase;
import com.arton.backend.infra.shared.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistUseCase artistUseCase;

    /**
     * 회원가입
     * 뮤지컬/콘서트 종류에 따라 아티스트 리스트를 보여준다.
     * Type: MUSICAL, CONCERT
     */
    @Operation(summary = "회원가입시 공연 찜 리스트", description = "회원가입시 공연 찜 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 불러오기 성공",
                    content = @Content( array = @ArraySchema(schema = @Schema(implementation = ArtistInterestDto.class))))})
    @GetMapping("/zzim")
    public ResponseEntity<ResponseData<List<ArtistInterestDto>>> showAllArtistForZzim(@RequestParam(name = "performanceType", required = true) String performanceType) {
        List<ArtistInterestDto> artistZzimDtos = artistUseCase.showArtistListForZzim(performanceType);
        ResponseData response = new ResponseData(
                "SUCCESS"
                , HttpStatus.OK.value()
                , artistZzimDtos
        );
        return ResponseEntity.ok().body(response);
    }


}
