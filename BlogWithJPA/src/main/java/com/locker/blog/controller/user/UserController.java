package com.locker.blog.controller.user;

import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.advice.exception.CEmailSigninFailedException;
import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.config.security.JwtTokenProvider;
import com.locker.blog.domain.response.CommonResult;
import com.locker.blog.domain.response.ListResult;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.service.response.ResponseService;
import com.locker.blog.service.user.EmailSendService;
import com.locker.blog.service.user.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JwtTokenProvider jwtTokenProvider;
    private final UserJpaRepo userJpaRepo;
    private final UserService userService;
    private final ResponseService responseService; // 결과를 처리할 Service
    private final PasswordEncoder passwordEncoder;
    private final EmailSendService emailSendService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "토큰으로 회원을 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang, @RequestParam String token) {
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String pk = jwtTokenProvider.getUserPk(token);
        logger.info("authentication's pk -> " + pk);

        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepo.findById(Long.parseLong(pk)).orElseThrow(CUserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정한다")
    @PutMapping(value = "/user/info")
    public SingleResult<User> modifyInfo(
            @ApiParam(value = "회원 번호", required = true) @RequestParam Long id,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String nickname,
            @ApiParam(value = "회원 소개", required = true) @RequestParam String introduction) {

        User user = userJpaRepo.findById(id).orElseThrow(CUserNotFoundException::new);
        user.setNickname(nickname);
        user.setIntroduction(introduction);

        return userService.modify(user);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 이미지 수정", notes = "회원 이미지를 수정한다")
    @PutMapping(value = "/user/image")
    public SingleResult<User> modifyImage(
            @ApiParam(value = "회원 번호", required = true) @RequestParam Long id,
            @ApiParam(value = "회원 이미지", required = true) @RequestParam String picture) {

        User user = userJpaRepo.findById(id).orElseThrow(CUserNotFoundException::new);
        user.setPicture(picture);
        return userService.modify(user);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 비밀번호 변경 권한", notes = "회원 비밀번호 수정 권한 여부를 확인한다")
    @PostMapping(value = "/user")
    public CommonResult checkAuthProvider(
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 제공처", required = true) @RequestParam String provider) {

        User user = userJpaRepo.findByEmailAndProvider(email,provider).orElseThrow(CUserNotFoundException::new);
        if(user.getProvider().equals("null")) return responseService.getSuccessResult();
        return responseService.getFailResult();
    }

    @ApiOperation(value = "회원 비밀번호 수정", notes = "회원 비밀번호를 수정한다")
    @PutMapping(value = "/user/password")
    public SingleResult<User> modifyPassword(
            @ApiParam(value = "회원 JWT 토큰", required = true) @RequestParam String token,
            @ApiParam(value = "회원 비밀 번호", required = true) @RequestParam String password,
            @ApiParam(value = "변경할 비밀 번호", required = true) @RequestParam String newPassword) {

        String pk = jwtTokenProvider.getUserPk(token);

        User user = userJpaRepo.findById(Long.parseLong(pk)).orElseThrow(CUserNotFoundException::new);
        if(passwordEncoder.matches(password,user.getPassword())) logger.info("[ 패스워드 변경 -> 본인 확인 완료 ]");
        else throw new CUserNotFoundException();
        user.setPassword(passwordEncoder.encode(newPassword));
        return userService.modify(user);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{id}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable Long id) {
        userJpaRepo.deleteById(id);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원 비밀번호 찾기", notes = "회원 비밀번호를 찾는다")
    @PutMapping(value = "/user/find/password")
    public CommonResult findPassword(
            @ApiParam(value = "회원 이메일", required = true) @RequestParam String email) {

        try {
            UUID uuid = emailSendService.sendFindPassword(email);
            User user = userJpaRepo.findByEmailAndProvider(email,"null").orElseThrow(CUserNotFoundException::new);
            user.setPassword(passwordEncoder.encode(uuid.toString()));
            userJpaRepo.save(user);
        } catch (CEmailSigninFailedException e) {
            throw e;
        }
        return responseService.getSuccessResult();
    }


    @ApiOperation(value = "회원 이미지 삭제", notes = "회원 이미지를 삭제한다.")
    @DeleteMapping(value = "/user/img/delete")
    public CommonResult deleteUserImg(
            @ApiParam(value = "회원 JWT 토큰", required = true) @RequestParam String token) {
        String pk = jwtTokenProvider.getUserPk(token);
        userService.deletePicture(Long.parseLong(pk));

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원 이미지 등록", notes = "회원 이미지를 등록한다.")
    @PutMapping(value = "/user/img/update")
    public CommonResult updateUserImg(
            @ApiParam(value = "회원 JWT 토큰", required = true) @RequestParam String token,
            @ApiParam(value = "회원 이미지", required = true) @RequestParam String picture) {
        String pk = jwtTokenProvider.getUserPk(token);
        userService.updatePicture(Long.parseLong(pk), picture);

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "회원 이미지 단건 조회", notes = "회원 이미지를 단건 조회한다.")
    @GetMapping(value = "/user/image")
    public SingleResult<String> getUserImg(
            @ApiParam(value="회원 고유 id", required = true) @RequestParam Long id
    ){
        String picture = userJpaRepo.findById(id).orElseThrow(CCommunicationException::new).getPicture();
        logger.info(picture);
        return responseService.getSingleResult(picture);
    }


}
