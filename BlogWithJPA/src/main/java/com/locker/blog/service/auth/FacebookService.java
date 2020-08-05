package com.locker.blog.service.auth;

import com.google.gson.Gson;
import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.domain.social.FbRetAuth;
import com.locker.blog.domain.social.GithubRetAuth;
import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.domain.user.GithubProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class FacebookService {
    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
    private String facebookClientId;

    @Value("${spring.security.oauth2.client.registration.facebook.clientSecret}")
    private String facebookClientSecret;

    @Value("${spring.security.oauth2.client.registration.facebook.redirectUri}")
    private String facebookRedirect;

    @Value("${spring.security.oauth2.client.provider.facebook.tokenUri}")
    private String facebookTokenUri;

    public GithubProfile getGithubProfile(String accessToken) {
        URI uri = URI.create("https://api.github.com/user?access_token=" + accessToken);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        System.out.println(response.getBody());

        try {
            // Request profile
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), GithubProfile.class);
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public FbRetAuth getFacebookToken(String code) {
        // Set http entity
        URI uri = URI.create(facebookTokenUri
                + "?client_id=" + facebookClientId
                + "&redirect_uri=" + facebookRedirect
                + "&client_secret=" + facebookClientSecret
                + "&code=" + code);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), FbRetAuth.class);
        }
        return null;
    }
}
