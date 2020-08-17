package com.locker.blog.repository.github;

import com.locker.blog.domain.repository.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyRepositoryJpaRepo extends JpaRepository<MyRepository,Long> {
    Optional<MyRepository> findByNameAndRepoName(String name, String repoName);
}
