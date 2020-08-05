package com.locker.blog.controller.social;


import com.locker.blog.domain.social.GithubRetAuth;
import com.locker.blog.domain.social.RetAuth;
import com.locker.blog.service.auth.GithubService;
import com.locker.blog.service.auth.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/social")
@CrossOrigin
public class GithubController {

    private final GithubService githubService;
    /**
     * 구 인증 완료 후 리다이렉트
     * /oauth2/authorization/github
     */
    @GetMapping(value = "/login/github")
    public GithubRetAuth redirectGoogle(@RequestParam String code, @RequestParam String state) {
        GithubRetAuth githubRetAuth = githubService.getGithubToken(code,state);
        return githubRetAuth;
    }
}