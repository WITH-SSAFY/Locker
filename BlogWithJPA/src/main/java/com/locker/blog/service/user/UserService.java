package com.locker.blog.service.user;

import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Transactional
@Service
@RequiredArgsConstructor
public class SocialSaveService {
    private final UserJpaRepo userJpaRepo;

    public void insert(String email, String provider, String name, String nickname, String picture) {
        userJpaRepo.save(User.builder()
                .email(email)
                .provider(provider)
                .name(name)
                .nickname(nickname)
                .picture(picture)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }
}
