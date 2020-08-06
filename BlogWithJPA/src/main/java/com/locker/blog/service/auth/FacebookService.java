package com.locker.blog.service.auth;

import com.google.gson.Gson;
import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.domain.social.FacebookRetAuth;
import com.locker.blog.domain.user.FacebookProfile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class FacebookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @Value("${spring.security.oauth2.client.provider.facebook.userInfoUri}")
    private String facebookUserInfoUri;

    public FacebookProfile getFacebookProfile(String accessToken) {
        // Set http entity
        URI uri = URI.create(facebookUserInfoUri
                + "?access_token=" + accessToken);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        logger.info(response.getBody());

        try {
            // Request profile
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), FacebookProfile.class);
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public FacebookRetAuth getFacebookToken(String code) {
        // Set http entity
        URI uri = URI.create(facebookTokenUri
                + "?client_id=" + facebookClientId
                + "&redirect_uri=" + facebookRedirect
                + "&client_secret=" + facebookClientSecret
                + "&code=" + code);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), FacebookRetAuth.class);
        }
        return null;
    }
}
