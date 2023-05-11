package com.arton.backend.infra.spotify;

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

    }
}