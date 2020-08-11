package com.locker.blog.service.tag;

import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.tag.PostTag;

import java.util.List;

public interface TagService {
    public Long isDuptag(String name);//중복된 태그 인가(중복인 경우 해당 tagid)
    public int insertNewTag(String name);//(중복이 아닐떄) 새로운 태그 추가
    public int connectTag(PostTag posttag);//posttag테이블에서 pid와 tagid연결
    public List<Post> selectAll(String name);//해당 태그를 가진 모든 리스트 출력
}
