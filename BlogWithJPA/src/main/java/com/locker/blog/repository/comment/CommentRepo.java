package com.locker.blog.repository.comment;

import com.locker.blog.domain.comment.Comment;

import java.util.List;

public interface CommentRepo {
    public int insert(Comment comment);//댓글 추가
    public int delete(Long rid);//삭제
    public List<Comment> selectAll(Long pid);//모든 댓글 불러오기
    public int update(Comment comment);//댓글 수정
}
