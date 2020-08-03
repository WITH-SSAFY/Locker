package com.locker.blog.controller.social;

import com.locker.blog.domain.social.FbRetAuth;
import com.locker.blog.domain.social.GithubRetAuth;
import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.service.auth.FacebookService;
import com.locker.blog.service.auth.GithubService;
import com.locker.blog.service.auth.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/social")
@CrossOrigin
public class FacebookController {

    private final FacebookService facebookService;
    private final Environment env;

    @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
    private String facebookClientId;

    @Value("${spring.security.oauth2.client.registration.facebook.redirectUri}")
    private String facebookRedirect;

    /**
     * 페이스북 로그인 페이지
     */
    @GetMapping(value = "/fb")
    public Map<String,String> socialLogin(Model model) {

        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.security.oauth2.client.provider.facebook.authorizationUri"))
                .append("?client_id=").append(facebookClientId)
                .append("&redirect_uri=").append(facebookRedirect)
                .append("&scope=public_profile,email");

        HashMap<String,String> map = new HashMap<>();
        map.put("loginUrl",loginUrl.toString());

        return map;
    }

    /**
     * 구 인증 완료 후 리다이렉트
     */
    @GetMapping(value = "/login/fb")
    public FbRetAuth redirectGoogle(@RequestParam String code) {
        FbRetAuth fbRetAuth = facebookService.getFacebookToken(code);
        System.out.println(fbRetAuth.toString());
        return fbRetAuth;
    }
}