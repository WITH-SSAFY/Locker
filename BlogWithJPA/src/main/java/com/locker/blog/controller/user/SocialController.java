package com.locker.blog.controller.user;

import com.locker.blog.domain.social.FacebookRetAuth;
import com.locker.blog.domain.social.GithubRetAuth;
import com.locker.blog.domain.social.NaverRetAuth;
import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.service.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApiIgnore
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/social")
@CrossOrigin
public class SocialController {

    private final Environment env;
    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final GithubService githubService;
    private final FacebookService facebookService;
    private final NaverService naverService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
    private String facebookClientId;

    @Value("${spring.security.oauth2.client.registration.facebook.redirectUri}")
    private String facebookRedirect;

    @Value("${spring.social.naver.clientId}")
    private String naverClientId;

    @Value("${spring.social.naver.redirectUri}")
    private String naverRedirect;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    /**
     * 카카오 로그인 페이지
     */
    @GetMapping(value = "/kakao")
    public Map<String,String> kakaoLogin() {

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


    /**
     * 네이버 로그인 페이지
     */
    @GetMapping(value = "/naver")
    public Map<String,String> naverLogin() {
        String state = generateRandomString();

        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.naver.url.login"))
                .append("&client_id=").append(naverClientId)
                .append("&redirect_uri=").append(baseUrl).append(naverRedirect)
                .append("&state=").append(state);

        HashMap<String,String> map = new HashMap<>();
        map.put("loginUrl",loginUrl.toString());

        return map;
    }

    /**
     * 네이버 인증 완료 후 리다이렉트
     * /api/social/login/naver
     */
    @GetMapping(value = "/login/naver")
    public NaverRetAuth redirectNaver(@RequestParam String code) {
        NaverRetAuth naverRetAuth = naverService.getNaverTokenInfo(code);
        return naverRetAuth;
    }


    /**
     * 구글 인증 완료 후 리다이렉트
     * /oauth2/authorization/google
     */
    @GetMapping(value = "/login/google")
    public RetAuth redirectGoogle(@RequestParam String code) {
        RetAuth retAuth = googleService.getGoogleTokenInfo(code);
        return retAuth;
    }

    /**
     * 깃헙 인증 완료 후 리다이렉트
     * /oauth2/authorization/github
     */
    @GetMapping(value = "/login/github")
    public String redirectGithub(@RequestParam String code, @RequestParam String state, Model model) {
        GithubRetAuth githubRetAuth = githubService.getGithubToken(code,state);
        model.addAttribute("token", githubRetAuth.getAccess_token());
        return "redirect:http://i3a606.p.ssafy.io/login/github";
    }

    /**
     * 페이스북 로그인 페이지
     */
    @GetMapping(value = "/facebook")
    public Map<String,String> facebookLogin() {

        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.security.oauth2.client.provider.facebook.authorizationUri"))
                .append("?client_id=").append(facebookClientId)
                .append("&redirect_uri=").append(baseUrl).append(facebookRedirect)
                .append("&scope=public_profile,email");

        HashMap<String,String> map = new HashMap<>();
        map.put("loginUrl",loginUrl.toString());

        return map;
    }

    /**
     * 페이스북 인증 완료 후 리다이렉트
     */
    @GetMapping(value = "/login/facebook")
    public FacebookRetAuth redirectFacebook(@RequestParam String code) {
        FacebookRetAuth facebookRetAuth = facebookService.getFacebookToken(code);
        return facebookRetAuth;
    }


    /* 세션 유효성 검증을 위한 난수 생성기 */
    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}
