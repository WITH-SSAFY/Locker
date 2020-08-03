package com.locker.blog.repository.post;

import com.locker.blog.domain.post.Post;

import java.util.ArrayList;
import java.util.List;

public interface PostDAO {
    public int insert(Post p);
    public int delete(String post_id);
    public List<Post> selectAll();
    public Post selectOne(String post_id);
    public int update(Post post);
    public List<Post> selectAllByWriter(String writer);
}
