package com.locker.blog.controller.repository;

import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.config.security.JwtTokenProvider;
import com.locker.blog.domain.response.ListResult;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.GithubProfile;
import com.locker.blog.domain.user.GithubRepository;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.service.auth.GithubService;
import com.locker.blog.service.response.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"6. Repository"})
@CrossOrigin
public class RepositoryController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final JwtTokenProvider jwtTokenProvider;
    private final GithubService githubService;
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "깃헙 레파지토리 정보", notes = "깃헙 레파지토리 정보를 가져온다.(전반적인 데이터)")
    @GetMapping(value = "github/repos")
    public ListResult<GithubRepository> githubRepositoryListResult (
            @ApiParam(value = "JWT 토큰", required = true) @RequestParam String token,
            @ApiParam(value = "깃헙 토큰", required = true) @RequestParam String accessToken) {
        String pk = jwtTokenProvider.getUserPk(token);

        User user = userJpaRepo.findById(Long.parseLong(pk)).orElseThrow(CUserNotFoundException::new);
        GithubProfile githubProfile = githubService.getGithubProfile(accessToken);
        List<GithubRepository> githubRepositoryList = githubService.getGithubRepo(githubProfile.getSubscriptions_url());

        logger.info(githubRepositoryList.toString());

        return responseService.getListResult(githubRepositoryList);
    }

    @ApiOperation(value = "깃헙 히든 정보", notes = "깃헙 히든 정보를 가져온다.")
    @GetMapping(value = "github/hidden")
    public SingleResult<String> githubHiddenSingleResult (@ApiParam(value = "깃헙 토큰", required = true) @RequestParam String accessToken) {
        GithubProfile githubProfile = githubService.getGithubProfile(accessToken);
        String hiddenInfo = githubService.getHiddenInfo(githubProfile.getLogin());

        return responseService.getSingleResult(hiddenInfo);
    }
}
