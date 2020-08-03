package com.locker.blog.repository.comment;

import com.locker.blog.domain.comment.Comment;

import java.util.List;

public interface CommentRepo {
    public int insert(Comment comment);//댓글 추가
    public int delete(Comment comment);//삭제
    public List<Comment> selectAll();//모든 댓글 불러오기
    public int update(Comment comment);//댓글 수정
    public int getRid(String pid);//해당 포스트에서 새로 추가될 댓글의 번호
}
