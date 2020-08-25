package com.locker.blog.repository.post;

import com.locker.blog.domain.post.PagingPost;
import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.repository.HotRepository;
import com.locker.blog.domain.tag.Tag;
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
    public List<Post> selectAllByWriter(Long usrId) {
        return session.selectList("post.selectAllByWriter", usrId);
    }

    @Override
    public int addLike(Long pid) {
        return session.update("post.addLike",pid);
    }

    @Override
    public List<Post> commonSearch(Map pageMap) {
        return session.selectList("post.commonSearch",pageMap);
    }

    @Override
    public List<Post> tagSearch(Map pageMap) {
        return session.selectList("post.tagSearch",pageMap);
    }

    @Override
    public Long getNextpid() {
        return session.selectOne("post.getNextpid");
    }

    @Override
    public Long getPrevPage(Long pid) {
        return session.selectOne("post.getPrevPage",pid);
    }

    @Override
    public Long getNextPage(Long pid) {
        return session.selectOne("post.getNextPage",pid);
    }

    @Override
    public List<HotRepository> getHotRepos() {
        return session.selectList("post.getHotRepos");
    }

    @Override
    public int updatePicture(Post post) {
        return session.update("post.updatePicture",post);
    }

    @Override
    public List<Post> getALLRepoPost(Long repo_id) {
        return session.selectList("post.getALLRepoPost",repo_id);
    }

    @Override
    public List<Post> getHotRepoPosts(Long repo_id) {
        return session.selectList("post.getHotRepoPosts",repo_id);
    }
}
