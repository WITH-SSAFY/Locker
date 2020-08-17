package com.locker.blog.service.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.domain.social.GithubRetAuth;
import com.locker.blog.domain.social.Languages;
import com.locker.blog.domain.user.*;
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
import java.util.List;

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

    public List<GithubRepository> getGithubRepo(String repoUrl) {
        URI uri = URI.create(repoUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);

        /* --- > Test for GithubRepository Entity Start
        ** String[] temp = response.getBody().split("},\\{");
        ** String oneData = temp[0].substring(1,temp[0].length()) + "}";

        ** logger.info("one data -> " + oneData);
        ** GithubRepository githubRepository = gson.fromJson(oneData,GithubRepository.class);
        ** logger.info("gson from one data -> " + githubRepository);
        ** --- > Test for GithubRepository Entity End */

        /*  --- > Test for GithubRepos Entity Start
        ** String multiData = "[" + oneData + "," + oneData + "]";
        ** logger.info("multi data -> " + multiData);
        ** List<GithubRepository> githubRepositoryList = gson.fromJson(multiData,new TypeToken<List<GithubRepository>>(){}.getType());
        ** logger.info("gson from multi data -> " + githubRepositoryList);
        ** --- > Test for GithubRepos Entity End */

        try {
            // Request profile
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), new TypeToken<List<GithubRepository>>(){}.getType());
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

    public String getHiddenInfo(String login) {
        URI uri = URI.create("https://raw.githubusercontent.com/" + login + "/" + login + "/master/README.md");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        //logger.info(response.getBody());

        try {
            // Request profile
            if (response.getStatusCode() == HttpStatus.OK)
                return response.getBody();
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public Languages getGithubLang(String name, String repo) {
        URI uri = URI.create("https://api.github.com/repos/" + name + "/" + repo + "/languages");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        // logger.info(response.getBody());

        try {
            if(response.getStatusCode() == HttpStatus.OK) return codeToRatio(gson.fromJson(response.getBody(),Languages.class));
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    private Languages codeToRatio(Languages beforeLang) {
        int cpp = Integer.parseInt(beforeLang.getCpp() == null ? "0" : beforeLang.getCpp());
        int c = Integer.parseInt(beforeLang.getC() == null ? "0" : beforeLang.getC());
        int css = Integer.parseInt(beforeLang.getCss() == null ? "0" : beforeLang.getCss());
        int vue = Integer.parseInt(beforeLang.getVue() == null ? "0" : beforeLang.getVue());
        int javascript = Integer.parseInt(beforeLang.getJavascript() == null ? "0" : beforeLang.getJavascript());
        int html = Integer.parseInt(beforeLang.getHtml() == null ? "0" : beforeLang.getHtml());
        int java = Integer.parseInt(beforeLang.getJava() == null ? "0" : beforeLang.getJava());

        int total = c + cpp + css + vue + javascript + html + java;
        Languages languages = new Languages();

        languages.setC(String.valueOf(c * 100 / total));
        languages.setCpp(String.valueOf((double)(cpp * 100 / total)));
        languages.setCss(String.valueOf(css * 100 / total));
        languages.setVue(String.valueOf(vue * 100 / total));
        languages.setJavascript(String.valueOf(javascript * 100 / total));
        languages.setHtml(String.valueOf(html * 100 / total));
        languages.setJava(String.valueOf((double)(java * 100 / total)));

        return languages;
    }
}
