package com.arton.backend.infra.spotify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("local")
@SpringBootTest
class SpotifyServiceTest {
    @Autowired
    SpotifyService spotifyService;

    @Test
    public void getAccessTokenTest() {
        String accessToken = spotifyService.getAccessToken();
        System.out.println("accessToken = " + accessToken);
    }

    @Test
    void getArtistsTest() {
        JsonObject 김동률 = spotifyService.getArtistsAsync("김동률");
        System.out.println("김동률 = " + 김동률);
    }

    @Test
    void searchTest() {
        String 르세라핌 = spotifyService.search("르세라핌");
        System.out.println("르세라핌 = " + 르세라핌);
    }
}