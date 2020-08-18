package com.locker.blog.repository.github;

import com.locker.blog.domain.repository.MyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepoRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager em;

    public List<MyRepository> findHotRepo() {
        logger.info("????");
        List<MyRepository> hotRepositories = em.createQuery("select r" +
                " from MyRepository r " +
                " order by r.likes desc",MyRepository.class)
                .setMaxResults(3)
                .getResultList();
        logger.info(hotRepositories.toString());
        return hotRepositories;
    }
}
