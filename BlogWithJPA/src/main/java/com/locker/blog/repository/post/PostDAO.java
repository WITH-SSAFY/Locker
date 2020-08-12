package com.locker.blog.repository.post;

import com.locker.blog.domain.post.PagingPost;
import com.locker.blog.domain.post.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PostDAO {
    public int insert(Post p);
    public int delete(String post_id);
    public List<PagingPost> selectAll(Map<String,Long> pageMap);
    public Post selectOne(String post_id);
    public int update(Post post);
    public List<Post> selectAllByWriter(String email);
    public int addLike(Long pid);
    public List<Post> commonSearch(String q);
}
