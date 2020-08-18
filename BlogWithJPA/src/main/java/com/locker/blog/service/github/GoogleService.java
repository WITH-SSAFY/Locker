package com.locker.blog.service.github;

import com.google.gson.Gson;
import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.domain.user.GoogleProfile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GoogleService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirectUri}")
    private String googleRedirect;

    public GoogleProfile getGoogleProfile(String accessToken) {
        URI uri = URI.create("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken);

        // Request profile
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        //logger.info(response.getBody());

        try {
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), GoogleProfile.class);
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public RetAuth getGoogleTokenInfo(String code) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirect);
        params.add("grant_type", "authorization_code");

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        URI uri = URI.create("https://oauth2.googleapis.com/token");

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), RetAuth.class);
        }
        return null;
    }
}