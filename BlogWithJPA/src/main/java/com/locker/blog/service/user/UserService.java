package com.locker.blog.service.user;

import com.locker.blog.advice.exception.CUserNotFoundException;
import com.locker.blog.domain.response.SingleResult;
import com.locker.blog.domain.user.User;
import com.locker.blog.repository.user.UserJpaRepo;
import com.locker.blog.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collections;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;
    private final EntityManager em;

    public void insert(String uid, String email, String provider, String name, String nickname, String picture) {
        userJpaRepo.save(User.builder()
                .uid(uid)
                .email(email)
                .provider(provider)
                .name(name)
                .nickname(nickname)
                .picture(picture)
                .verify(true)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    public SingleResult<User> modify(User user) {
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    /**
     * 사진 삭제
     * @param id
     */
    public void deletePicture(Long id) {
        int isSuccessful = em.createQuery("Update User u set u.picture=null where u.id=:id")
                .setParameter("id", id)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new CUserNotFoundException();
        }
    }

    public void updatePicture(Long id, String picture) {
        int isSuccessful = em.createQuery("Update User u set u.picture=:picture where u.id=:id")
                .setParameter("id", id)
                .setParameter("picture",picture)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new CUserNotFoundException();
        }
    }
}
