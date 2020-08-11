package com.locker.blog.controller.repository;

import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.domain.response.ListResult;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.GithubProfile;
import com.locker.blog.domain.user.GithubRepository;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.service.auth.GithubService;
import com.locker.blog.service.response.ResponseService;
import io.swagger.annotations.Api;
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

    private final GithubService githubService;
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @GetMapping(value = "github/repos")
    public ListResult<GithubRepository> githubRepositoryListResult (
                                        @RequestParam String email,
                                        @RequestParam String accessToken) {
        User user = userJpaRepo.findByEmailAndProvider(email,"github").orElseThrow(CUserNotFoundException::new);

        GithubProfile githubProfile = githubService.getGithubProfile(accessToken);
        List<GithubRepository> githubRepositoryList = githubService.getGithubRepo(githubProfile.getSubscriptions_url());

        logger.info(githubRepositoryList.toString());

        return responseService.getListResult(githubRepositoryList);
    }
}
