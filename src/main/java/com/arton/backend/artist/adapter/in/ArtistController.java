package com.arton.backend.artist.adapter.in;

import com.arton.backend.artist.application.data.ArtistInterestDetailDTO;
import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.artist.application.data.CrawlerArtistRegistDTO;
import com.arton.backend.artist.application.data.SpotifyRegistDTO;
import com.arton.backend.artist.application.port.in.ArtistUseCase;
import com.arton.backend.artist.application.port.in.CrawlerEnrollUseCase;
import com.arton.backend.artist.application.port.in.SpotifyEnrollUseCase;
import com.arton.backend.infra.shared.common.CommonResponse;
import com.arton.backend.infra.shared.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistUseCase artistUseCase;
    private final SpotifyEnrollUseCase spotifyEnrollUseCase;
    private final CrawlerEnrollUseCase crawlerEnrollUseCase;
    /**
     * 회원가입
     * 뮤지컬/콘서트 종류에 따라 아티스트 리스트를 보여준다.
     * Type: MUSICAL, CONCERT
     */
    @Operation(summary = "회원가입시 공연 찜 리스트", description = "회원가입시 공연 찜 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리스트 불러오기 성공",
                    content = @Content( schema = @Schema(implementation = ArtistInterestDetailDTO.class)))})
    @GetMapping("/artist/zzim")
    public ResponseEntity<ResponseData<ArtistInterestDetailDTO>> showAllArtistForZzim(@PageableDefault(size = 9)Pageable pageable) {
        ArtistInterestDetailDTO artistZzimDtos = artistUseCase.showArtistListForZzim(pageable);
        ResponseData response = new ResponseData(
                "SUCCESS"
                , HttpStatus.OK.value()
                , artistZzimDtos
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/artist/search")
    public ResponseEntity<List<CommonArtistDto>> showAllArtistForZzim(@RequestParam(required = false, name = "name") String name) {
        List<CommonArtistDto> artists = artistUseCase.findByName(name);
        return ResponseEntity.ok(artists);
    }

    @PostMapping("/spotify")
    public ResponseEntity<CommonResponse> enrollBySpotify(@Valid @RequestBody SpotifyRegistDTO spotifyRegistDTO){
        spotifyEnrollUseCase.enrollArtistBySpotify(spotifyRegistDTO);
        CommonResponse response = CommonResponse.builder().status(200).message("아티스트 등록에 성공하였습니다.").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/artist/crawl/add")
    public ResponseEntity<CommonResponse> enrollByCrawler(@Valid @RequestBody CrawlerArtistRegistDTO crawlerArtistRegistDTO){
        crawlerEnrollUseCase.enrollArtistByCrawler(crawlerArtistRegistDTO);
        CommonResponse response = CommonResponse.builder().status(200).message("아티스트 등록에 성공하였습니다.").build();
        return ResponseEntity.ok(response);
    }


}
