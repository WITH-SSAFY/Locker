package com.locker.blog.repository.user;

import com.locker.blog.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User,Long> {
    Optional<User> findByUid(String uid);
    Optional<User> findByUidAndProvider(String uid, String provider);
}
