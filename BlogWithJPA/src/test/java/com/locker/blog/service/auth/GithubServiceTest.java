package com.locker.blog.service.auth;

import com.google.gson.Gson;
import com.locker.blog.domain.user.GithubRepository;
import com.locker.blog.service.auth.GithubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringBootTest
public class GithubServiceTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private GithubService githubService;
    @Autowired private Gson gson;

    @Test
    public void getGithubRepoTest() throws Exception {
        // given
        final String repoUrl = "https://api.github.com/users/junhok82/subscriptions";

        // when
        List<GithubRepository> githubRepositoryList =  githubService.getGithubRepo(repoUrl);

        // then
        logger.info(githubRepositoryList.toString());
    }

    @Test
    public void getHiddenInfoTest() throws Exception {
        // given
        String loginId = "junhok82";

        // when
        String hiddenInfo = githubService.getHiddenInfo(loginId);

        // then
        logger.info(hiddenInfo);
     }
}
