package com.arton.backend.infra.spotify;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

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
}
