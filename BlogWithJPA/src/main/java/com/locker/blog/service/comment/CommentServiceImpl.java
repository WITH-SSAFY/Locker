package com.locker.blog.service.comment;

import com.locker.blog.domain.comment.Comment;
import com.locker.blog.repository.comment.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepo commentRepo;

    @Override
    public int insert(Comment comment) {
        return commentRepo.insert(comment);
    }

    @Override
    public int delete(Long rid) {
        return commentRepo.delete(rid);
    }

    @Override
    public List<Comment> selectAll(Long pid) {
        return commentRepo.selectAll(pid);
    }

    @Override
    public Comment selectOne(Comment comment) {
        return commentRepo.selectOne(comment);
    }

    @Override
    public int update(Comment comment) {
        return commentRepo.update(comment);
    }


}
