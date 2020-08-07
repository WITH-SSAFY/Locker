package com.locker.blog.repository.post;

import com.locker.blog.domain.post.PagingPost;
import com.locker.blog.domain.post.Post;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PostDAOImpl implements PostDAO {
    @Autowired
    SqlSession session;

    @Override
    public int insert(Post p) {
        return session.insert("post.insert", p);
    }

    @Override
    public int delete(String post_id) {
        return session.delete("post.delete", post_id);
    }

    @Override
    public List<PagingPost> selectAll(Map<String,Long> pageMap) {
        return session.selectList("post.selectAll",pageMap);
    }

    @Override
    public Post selectOne(String post_id) {
        return session.selectOne("post.selectOne", post_id);
    }

    @Override
    public int update(Post post) {
        return session.update("post.update", post);
    }

    @Override
    public List<Post> selectAllByWriter(String email) {
        return session.selectList("post.selectAllByWriter", email);
    }

    @Override
    public int addLike(Long pid) {
        return session.update("post.addLike",pid);
    }


}
