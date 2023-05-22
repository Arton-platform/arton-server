package com.arton.backend.infra.spotify;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.google.gson.JsonObject;
import com.neovisionaries.i18n.CountryCode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.Optional;

@Service
public class SpotifyService {

    private final String id;
    private final String password;

    private final SpotifyApi spotifyApi;

    public SpotifyService(@Value("${spotify.id}") String id, @Value("${spotify.password}") String password) {
        this.id = id;
        this.password = password;
        this.spotifyApi = SpotifyApi.builder().setClientId(id).setClientSecret(password).build();
    }

    public String getAccessToken() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(id)
                .setClientSecret(password)
                .build();

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        // do
        try{
            ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            String accessToken = clientCredentials.getAccessToken();
            return accessToken;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public JsonObject getArtistsAsync(String keyword) {
        spotifyApi.setAccessToken(getAccessToken());
        SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(keyword)
                .market(CountryCode.KR)
                .limit(1)
                .build();
        JsonObject result = new JsonObject();
        try {
            Paging<Artist> artistPaging = searchArtistsRequest.execute();
            Artist[] items = artistPaging.getItems();
            if (!ObjectUtils.isEmpty(items)) {
                Artist item = items[0];
                System.out.println("item = " + item);
                result.addProperty("name", item.getName());
                result.addProperty("imageUrl", "");
                if (!ObjectUtils.isEmpty(item.getImages())) {
                    result.addProperty("imageUrl", item.getImages()[0].getUrl());
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String search(String keyword) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + getAccessToken());;
        httpHeaders.add("Host", "api.spotify.com");
        httpHeaders.add("Content-type", "application/json");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://api.spotify.com/v1/search?type=artist&market=KR&limit=1&q=" + keyword, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value(); //상태 코드가 들어갈 status 변수
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return response;
    }
}
