package com.arton.backend.artist.application.service;

import com.arton.backend.artist.application.data.ArtistInterestDetailDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ArtistServiceTest {

    @Autowired
    ArtistService artistService;


    @Test
    void getZzimDTOTest() {
        Pageable pageable = PageRequest.of(0, 9);
        ArtistInterestDetailDTO artistInterestDetailDTO = artistService.showArtistListForZzim(pageable);
//        Assertions.assertThat(artistInterestDetailDTO.getConcerts().size()).isEqualTo(10);
//        Assertions.assertThat(artistInterestDetailDTO.getMusicals().size()).isEqualTo(10);
        System.out.println("콘서트 출연한 Artist  = " + artistInterestDetailDTO.getConcerts());
        System.out.println("뮤지컬 출연한 Artist  = " + artistInterestDetailDTO.getMusicals());
    }
}