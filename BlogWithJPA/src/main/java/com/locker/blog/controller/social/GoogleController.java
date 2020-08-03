package com.locker.blog.controller.social;

import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.service.auth.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/social")
@CrossOrigin
public class GoogleController {

    private final GoogleService googleService;
    /**
     * 구 인증 완료 후 리다이렉트
     * /oauth2/authorization/google
     */
    @GetMapping(value = "/login/google")
    public RetAuth redirectGoogle(@RequestParam String code) {
        RetAuth retAuth = googleService.getGoogleTokenInfo(code);
        return retAuth;
    }
}
