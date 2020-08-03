package com.locker.blog.controller.social;

import com.google.gson.Gson;
import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.service.auth.KakaoService;
import com.locker.blog.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/social")
@CrossOrigin
public class KakaoController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final KakaoService kakaoService;
    private final ResponseService responseService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    /**
     * 카카오 로그인 페이지
     */
    @GetMapping(value = "/kakao")
    public Map<String,String> socialLogin(Model model) {

        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);

        HashMap<String,String> map = new HashMap<>();
        map.put("loginUrl",loginUrl.toString());

        return map;
    }

    /**
     * 카카오 인증 완료 후 리다이렉트
     */
    @GetMapping(value = "/login/kakao")
    public RetAuth redirectKakao(@RequestParam String code) {
        RetAuth retAuth = kakaoService.getKakaoTokenInfo(code);
        return retAuth;
    }
}