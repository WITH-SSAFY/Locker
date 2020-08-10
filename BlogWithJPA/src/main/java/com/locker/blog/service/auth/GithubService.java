package com.locker.blog.service.auth;

import com.google.gson.Gson;
import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.domain.social.GithubRetAuth;
import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.domain.user.GithubProfile;
import com.locker.blog.domain.user.GoogleProfile;
import com.locker.blog.domain.user.KakaoProfile;
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
public class GithubService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
    private String githubClientSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirectUri}")
    private String githubRedirect;

    public GithubProfile getGithubProfile(String accessToken) {
        URI uri = URI.create("https://api.github.com/user?access_token=" + accessToken);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        logger.info(response.getBody());

        try {
            // Request profile
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), GithubProfile.class);
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public GithubRetAuth getGithubToken(String code, String state) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", githubClientId);
        params.add("client_secret", githubClientSecret);
        params.add("code", code);
        params.add("redirect_uri", githubRedirect);
        params.add("state", state);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        URI uri = URI.create("https://github.com/login/oauth/access_token");

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        logger.info("github token response -> " + response.getBody());

        if (response.getStatusCode() == HttpStatus.OK) {
            GithubRetAuth githubRetAuth = new GithubRetAuth();
            String[] output = response.getBody().split("&");
            for (int i = 0; i < output.length; i++) {
                String[] str = output[i].split("=");
                if(str[0].equals("access_token")) githubRetAuth.setAccess_token(str[1]);
                else if(str[0].equals("scope")) githubRetAuth.setScope(str[1]);
                else if(str[0].equals("token_type")) githubRetAuth.setToken_type(str[1]);
            }
            return githubRetAuth;
        }
        return null;
    }
}
