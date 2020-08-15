package com.locker.blog.repository.postlike;

import com.locker.blog.domain.postlike.Postlike;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class PostlikeRepoImpl implements PostlikeRepo{
    @Autowired
    SqlSession sqlSession;

    @Override
    public int addLikePost(Long pid) {
        return sqlSession.update("postlike.addLikePost",pid);
    }

    @Override
    public int addUserLikePost(Map ids) {
        return sqlSession.insert("postlike.addUserLikePost",ids);
    }

    @Override
    public int deleteLikePost(Long pid) {
        return sqlSession.update("postlike.deleteLikePost",pid);
    }

    @Override
    public int deleteUserLikePost(Map ids) {
        return sqlSession.delete("postlike.deleteUserLikePost",ids);
    }

    @Override
    public Long getLikes(Long pid) {
        return sqlSession.selectOne("postlike.getLikes",pid);
    }
}
