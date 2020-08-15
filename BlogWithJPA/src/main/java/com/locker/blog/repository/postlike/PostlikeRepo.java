package com.locker.blog.repository.postlike;

import com.locker.blog.domain.postlike.Postlike;

import java.util.Map;

public interface PostlikeRepo {
    public int addLikePost(Long pid);//포스트에 좋아요 추가
    public int addUserLikePost(Map ids);//사용자가 해당 포스트에 좋아요 누른것 표시
    public int deleteLikePost(Long pid);//포스트에 좋아요 삭제
    public int deleteUserLikePost(Map ids);//사용자가 해당 포스트에 좋아요 누른것 삭제
    public Postlike getLikes(Long pid);//포스트의 좋아요 수 출력
}
