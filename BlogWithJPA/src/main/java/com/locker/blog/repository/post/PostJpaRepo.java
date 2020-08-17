package com.locker.blog.repository.post;

import com.locker.blog.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    Optional<List<Post>> findAllByOrderByLikesDesc();
}
