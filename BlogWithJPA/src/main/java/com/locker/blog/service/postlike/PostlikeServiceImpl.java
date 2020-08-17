package com.locker.blog.service.postlike;

import com.locker.blog.domain.postlike.Postlike;
import com.locker.blog.repository.postlike.PostlikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PostlikeServiceImpl implements PostlikeService{
    @Autowired
    PostlikeRepo postlikeRepo;

    @Override
    public int addLikePost(Long pid) {
        return postlikeRepo.addLikePost(pid);
    }

    @Override
    public int addUserLikePost(Map ids) {
        return postlikeRepo.addUserLikePost(ids);
    }

    @Override
    public int deleteLikePost(Long pid) {
        return postlikeRepo.deleteLikePost(pid);
    }

    @Override
    public int deleteUserLikePost(Map ids) {
        return postlikeRepo.deleteUserLikePost(ids);
    }

    @Override
    public Long getLikes(Long pid) {
        return postlikeRepo.getLikes(pid);
    }

    @Override
    public int checkUserLiked(Map ids) {
        return postlikeRepo.checkUserLiked(ids);
    }
}
