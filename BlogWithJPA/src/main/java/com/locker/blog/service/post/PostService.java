package com.locker.blog.service.post;

import com.locker.blog.domain.post.PagingPost;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.repository.HotRepository;

import java.util.List;
import java.util.Map;

public interface PostService {
    public int insert(Post p);
    public int delete(String post_id);
    public Post selectOne(String post_id);
    public int update(Post post);
    public List<PagingPost> selectAll(Map<String,Long> pageMap);
    public List<Post> selectAllByWriter(Long usr_id);
    public int addLike(Long pid);
    public List<Post> commonSearch(Map pageMap);
    public List<Post> tagSearch(Map pageMap);
    public Long getNextpid();
    public Long getPrevPage(Long pid);
    public Long getNextPage(Long pid);
    public List<HotRepository> getHotRepos();
    public int updatePicture(Post post);
}
