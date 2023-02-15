package com.arton.backend.user.application.service;

import com.arton.backend.artist.application.data.CommonArtistDto;
import com.arton.backend.user.application.data.MainPageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MainPageServiceTest {

    @Autowired
    MainPageService mainPageService;

    @Test
    void getMainPageTest() {
        MainPageDto mainPage = mainPageService.getMainPage(1L);
        System.out.println("mainPage = " + mainPage);
        List<CommonArtistDto> artists = mainPage.getArtists();
    }
}