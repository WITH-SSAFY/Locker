package com.locker.blog.service.tag;

import com.locker.blog.domain.post.Post;
import com.locker.blog.domain.tag.PostTag;
import com.locker.blog.domain.tag.Tag;

import java.util.List;

public interface TagService {
    public Tag isDuptag(String name);//중복된 태그 인가(중복인 경우 해당 tagid)
    public int insertNewTag(Tag tag);//(중복이 아닐떄) 새로운 태그 추가
    public int connectTag(PostTag posttag);//posttag테이블에서 pid와 tagid연결
    public List<String> getTags(Long pid);//해당 포스트의 모든 태그 출력
    public List<Tag> getAllTags(Long pid);//해당 포스트의 모든 태그 정보 출력
    public int deleteTag(Long pid);//해당 포스트에 달린 모든 태그 삭제(수정시 필요)
}
