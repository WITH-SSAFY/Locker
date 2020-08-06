package com.locker.blog.controller.user;

import com.locker.blog.advice.exception.CEmailDuplicatedException;
import com.locker.blog.advice.exception.CEmailSigninFailedException;
import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.domain.response.CommonResult;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.*;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.config.security.JwtTokenProvider;
import com.locker.blog.service.auth.*;
import com.locker.blog.service.response.ResponseService;
import com.locker.blog.service.user.EmailSendService;
import com.locker.blog.service.user.SocialSaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class SignController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSendService emailSendService;
    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final GithubService githubService;
    private final FacebookService facebookService;
    private final NaverService naverService;
    private final SocialSaveService socialSaveService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String email,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();

        logger.info(user.toString());
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));

    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signin(
                               @ApiParam(value = "이메일 : 이메일", required = true) @RequestParam String email,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name,
                               @ApiParam(value = "닉네임", required = true) @RequestParam String nickname) {

        userJpaRepo.save(User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .roles(Collections.singletonList("ROLE_USER"))
                .provider("null")
                .build());
        return responseService.getSuccessResult();
    }


    @ApiOperation(value = "중복 체크", notes = "아이디 중복을 확인 한다.")
    @PostMapping(value = "/duplicate")
    public CommonResult checkDuplicate (@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String email) {
        User noUser = new User();
        noUser.setEmail("can't found");

        User user = userJpaRepo.findByEmail(email).orElse(noUser);
        if(email.matches(user.getEmail())) {
            throw new CEmailDuplicatedException();
        }
        return responseService.getSuccessResult();
    }


    @ApiOperation(value = "이메일 인증", notes = "이메일로 인증 메세지를 보낸다.")
    @PostMapping("/verify")
    public CommonResult verify(@ApiParam(value = "이메일 인증", required = true) @RequestParam String email) {
        try {
            emailSendService.sendVerificationMail(email);
        } catch (CEmailSigninFailedException e) {
            throw e;
        }
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true) @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile kakaoProfile = null;
        GoogleProfile googleProfile = null;
        GithubProfile githubProfile = null;
        FacebookProfile facebookProfile = null;
        NaverProfile naverProfile = null;

        String uid = null;
        String name = null;
        String nickname = null;
        String email = null;
        String picture = null;

        // get profile
        if(provider.equals("kakao")) {
            kakaoProfile = kakaoService.getKakaoProfile(accessToken);
            uid = String.valueOf(kakaoProfile.getId());
            email = String.valueOf(kakaoProfile.getKakao_account().getEmail());
            name = String.valueOf(kakaoProfile.getProperties().getNickname());
            nickname = name;
            picture = String.valueOf(kakaoProfile.getProperties().getProfile_image());

            // logger
            logger.info(kakaoProfile.toString());
        }
        else if(provider.equals("google")) {
            googleProfile = googleService.getGoogleProfile(accessToken);
            uid = String.valueOf(googleProfile.getId());
            email = String.valueOf(googleProfile.getEmail());
            name = String.valueOf(googleProfile.getName());
            nickname = name;
            picture = String.valueOf(googleProfile.getPicture());
        }
        else if(provider.equals("github")) {
            githubProfile = githubService.getGithubProfile(accessToken);
            uid = String.valueOf(githubProfile.getId());
            email = String.valueOf(githubProfile.getEmail());
            name = String.valueOf(githubProfile.getName());
            nickname = name;
            picture = String.valueOf(githubProfile.getAvatar_url());
        }
        else if(provider.equals("facebook")) {
            facebookProfile = facebookService.getFacebookProfile(accessToken);
            uid = String.valueOf(facebookProfile.getId());
            email = String.valueOf(facebookProfile.getEmail());
            name = String.valueOf(facebookProfile.getName());
            nickname = name;
            picture = String.valueOf(facebookProfile.getPicture().getData().getUrl());
        }
        else if(provider.equals("naver")) {
            naverProfile = naverService.getNaverProfile(accessToken);
            uid = String.valueOf(naverProfile.getResponse().getId());
            email = String.valueOf(naverProfile.getResponse().getEmail());
            nickname = String.valueOf(naverProfile.getResponse().getNickname());
            name = String.valueOf(naverProfile.getResponse().getName());
        }

        logger.info("uid : " + uid + " name : " + name + " picture : " + picture + " email : " + email + " provider : " + provider);

        Optional<User> user = userJpaRepo.findByEmailAndProvider(email, provider);
        if(user.isPresent()) {
            return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.get().getId()), user.get().getRoles()));
        }

        socialSaveService.insert(email,provider,name,nickname,picture);
        User findUser = userJpaRepo.findByEmailAndProvider(email,provider).orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(findUser.getId()), findUser.getRoles()));
    }

}