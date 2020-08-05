package com.locker.blog.service.post;

import com.locker.blog.domain.post.Post;
import com.locker.blog.repository.post.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDAO dao;

    @Override
    public int insert(Post p) {
        return dao.insert(p);
    }

    @Override
    public int delete(String post_id) {
        return dao.delete(post_id);
    }

    @Override
    public Post selectOne(String post_id) {
        return dao.selectOne(post_id);
    }

    @Override
    public int update(Post post) {
        return dao.update(post);
    }

    @Override
    public List<Post> selectAll() {
        return dao.selectAll();
    }

    @Override
    public List<Post> selectAllByWriter(String email) {
        return dao.selectAllByWriter(email);
    }

}
