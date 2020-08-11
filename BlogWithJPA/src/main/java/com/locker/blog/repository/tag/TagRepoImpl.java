package com.locker.blog.repository.tag;

import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.tag.PostTag;
import com.locker.blog.domain.tag.Tag;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TagRepoImpl implements TagRepo{
    @Autowired
    SqlSession sqlSession;


    @Override
    public Tag isDuptag(String name) {
        return sqlSession.selectOne("tag.isDuptag",name);
    }

    @Override
    public int insertNewTag(Tag tag) {
        return sqlSession.insert("tag.insertNewTag",tag);
    }

    @Override
    public int connectTag(PostTag posttag) {
        return sqlSession.insert("tag.connectTag",posttag);
    }

    @Override
    public List<Tag> getTags(Long pid) {
        return sqlSession.selectList("tag.getTags",pid);
    }

}
