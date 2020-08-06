package com.locker.blog.repository.comment;

import com.locker.blog.domain.comment.Comment;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CommentRepoImpl implements CommentRepo{
    @Autowired
    SqlSession sqlSession;

    @Override
    public int insert(Comment comment) {
        return sqlSession.insert("comment.insert", comment);
    }

    @Override
    public int delete(Long rid) {
        return sqlSession.delete("comment.delete", rid);
    }

    @Override
    public List<Comment> selectAll(Long pid) {
        return sqlSession.selectList("comment.selectAll",pid);
    }

    @Override
    public int update(Comment comment) {
        return sqlSession.update("comment.update",comment);
    }
}
