package com.locker.blog.controller.user;

import com.locker.blog.advice.exception.CEmailDuplicatedException;
import com.locker.blog.advice.exception.CEmailSigninFailedException;
import com.locker.blog.advice.exception.CUserExistException;
import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.domain.response.CommonResult;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.KakaoProfile;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.config.security.JwtTokenProvider;
import com.locker.blog.service.response.ResponseService;
import com.locker.blog.service.user.EmailSendService;
import com.locker.blog.service.user.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSendService emailSendService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        User user = userJpaRepo.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));

    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String uid,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name,
                               @ApiParam(value = "닉네임", required = true) @RequestParam String nickname) {

        userJpaRepo.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .roles(Collections.singletonList("ROLE_USER"))
                .createDate(LocalDateTime.now())
                .provider("null")
                .build());
        return responseService.getSuccessResult();
    }


    @ApiOperation(value = "중복 체크", notes = "아이디 중복을 확인 한다.")
    @PostMapping(value = "/duplicate")
    public CommonResult checkDuplicate (@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String uid) {
        User noUser = new User();
        noUser.setUid("can't found");

        User user = userJpaRepo.findByUid(uid).orElse(noUser);
        if(uid.matches(user.getUid())) {
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

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);

        User user = userJpaRepo.findByUidAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true) @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "이름", required = true) @RequestParam String name) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<User> user = userJpaRepo.findByUidAndProvider(String.valueOf(profile.getId()), provider);
        if(user.isPresent()) throw new CUserExistException();

        userJpaRepo.save(User.builder()
                .uid(String.valueOf(profile.getId()))
                .provider(provider)
                .name(name)
                .nickname(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .createDate(LocalDateTime.now())
                .build());

        return responseService.getSuccessResult();
    }
}