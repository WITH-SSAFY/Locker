package com.locker.blog.service.post;

import com.locker.blog.domain.post.PagingPost;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.repository.HotRepository;
import com.locker.blog.repository.post.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<PagingPost> selectAll(Map<String,Long> pageMap) {
        return dao.selectAll(pageMap);
    }

    @Override
    public List<Post> selectAllByWriter(Long usrId) {
        return dao.selectAllByWriter(usrId);
    }

    @Override
    public int addLike(Long pid) {
        return dao.addLike(pid);
    }

    @Override
    public List<Post> commonSearch(Map pageMap) {
        return dao.commonSearch(pageMap);
    }

    @Override
    public List<Post> tagSearch(Map pageMap) {
        return dao.tagSearch(pageMap);
    }

    @Override
    public Long getNextpid() {
        return dao.getNextpid();
    }

    @Override
    public Long getPrevPage(Long pid) {
        return dao.getPrevPage(pid);
    }

    @Override
    public Long getNextPage(Long pid) {
        return dao.getNextPage(pid);
    }

    @Override
    public List<HotRepository> getHotRepos() {
        return dao.getHotRepos();
    }

    @Override
    public int updatePicture(Post post) {
        return dao.updatePicture(post);
    }

    @Override
    public List<Post> getALLRepoPost(Long repo_id) {
        return dao.getALLRepoPost(repo_id);
    }

}
