package com.locker.blog.repository.tag;

import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.tag.PostTag;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TagRepoImpl implements TagRepo{
    @Autowired
    SqlSession sqlSession;


    @Override
    public Long isDuptag(String name) {
        return sqlSession.selectOne("tag.isDuptag",name);
    }

    @Override
    public int insertNewTag(String name) {
        return sqlSession.insert("tag.insertNewTag",name);
    }

    @Override
    public int connectTag(PostTag posttag) {
        return sqlSession.insert("tag.connectTag",posttag);
    }

    @Override
    public List<Post> selectAll(String name) {
        return sqlSession.selectList("tag.selectAll",name);
    }
}
