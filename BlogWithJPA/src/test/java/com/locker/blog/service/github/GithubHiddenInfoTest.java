package com.locker.blog.service.github;

import com.google.gson.Gson;
import com.locker.blog.repository.github.MyRepositoryJpaRepo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class GithubHiddenInfoTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private GithubService githubService;
    @Autowired private Gson gson;
    @Autowired private MyRepositoryJpaRepo myRepositoryJpaRepo;

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
