package com.locker.blog.repository.post;

import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.repository.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    Optional<List<Post>> findAllByOrderByLikesDesc();
    Optional<List<Post>> findAllByRepoIdOrderByCreated(Long repoId);
    //Optional<List<Post>> findAllByMyRepository(MyRepository myRepository);
 }
