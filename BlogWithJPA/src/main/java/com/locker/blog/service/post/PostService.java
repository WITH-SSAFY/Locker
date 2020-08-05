package com.locker.blog.service.post;

import com.locker.blog.domain.post.Post;

import java.util.List;

public interface PostService {
    public int insert(Post p);
    public int delete(String post_id);
    public Post selectOne(String post_id);
    public int update(Post post);
    public List<Post> selectAll();
    public List<Post> selectAllByWriter(String email);
}
