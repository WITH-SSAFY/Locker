package com.locker.blog.controller.user;

import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.domain.response.CommonResult;
import com.locker.blog.domain.response.ListResult;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.service.response.ResponseService;
import com.locker.blog.service.user.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final UserService userService;
    private final ResponseService responseService; // 결과를 처리할 Service
    private final PasswordEncoder passwordEncoder;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "토큰으로 회원을 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepo.findByEmail(id).orElseThrow(CUserNotFoundException::new));
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 비밀번호 수정", notes = "회원 비밀번호를 수정한다")
    @PutMapping(value = "/user/password")
    public SingleResult<User> modifyPassword(
            @ApiParam(value = "회원 번호", required = true) @RequestParam Long id,
            @ApiParam(value = "회원 비밀 번호", required = true) @RequestParam String password) {

        User user = userJpaRepo.findById(id).orElseThrow(CUserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(password));
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
}
