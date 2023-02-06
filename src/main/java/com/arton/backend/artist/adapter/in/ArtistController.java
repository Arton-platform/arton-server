package com.arton.backend.artist.adapter.in;

import com.arton.backend.artist.application.port.in.ArtistUseCase;
import com.arton.backend.artist.application.data.ArtistInterestDto;
import com.arton.backend.artist.application.data.ListArtistInterestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/zzim")
    public ResponseEntity<ListArtistInterestDto> showAllArtistForZzim(@RequestParam(name = "performanceType", required = true) String performanceType) {
        List<ArtistInterestDto> artistZzimDtos = artistUseCase.showArtistListForZzim(performanceType);
        ListArtistInterestDto response = ListArtistInterestDto.builder()
                .artists(artistZzimDtos)
                .build();
        return ResponseEntity.ok(response);
    }


}
