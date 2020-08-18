package com.locker.blog.service.github;

import com.google.gson.Gson;
import com.locker.blog.domain.repository.GithubRepository;
import com.locker.blog.repository.github.MyRepositoryJpaRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class GithubRepoTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private GithubService githubService;
    @Autowired private Gson gson;
    @Autowired private MyRepositoryJpaRepo myRepositoryJpaRepo;

    @Test
    public void getGithubRepoTest() throws Exception {
        // given
        final String repoUrl = "https://api.github.com/users/junhok82/subscriptions";

        // when
        List<GithubRepository> githubRepositoryList =  githubService.getGithubRepo(repoUrl);

        // then
        logger.info(githubRepositoryList.toString());
    }
}
