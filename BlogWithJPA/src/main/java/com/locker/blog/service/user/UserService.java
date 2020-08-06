package com.locker.blog.service.user;

import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

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

    public SingleResult<User> modify(User user) {
        return responseService.getSingleResult(userJpaRepo.save(user));
    }
}
