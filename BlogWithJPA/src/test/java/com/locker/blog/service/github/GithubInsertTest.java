package com.locker.blog.service.github;

import com.google.gson.Gson;
import com.locker.blog.advice.exception.CCommunicationException;
import com.locker.blog.domain.repository.MyRepository;
import com.locker.blog.repository.github.MyRepositoryJpaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class GithubInsertTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private GithubService githubService;
    @Autowired private Gson gson;
    @Autowired private MyRepositoryJpaRepo myRepositoryJpaRepo;

     @Test
     public void insertMyRepository() throws Exception {
         // given
         String name = "test1234";
         String repoName = "test1234";

         // when
         myRepositoryJpaRepo.save(MyRepository.builder()
                 .name(name)
                 .repoName(repoName)
                 .build());

         // then
         MyRepository myRepository = myRepositoryJpaRepo.findByNameAndRepoName(name,repoName).orElseThrow(ClassNotFoundException::new);
         Assertions.assertEquals(myRepository.getName(),name);
      }
}
