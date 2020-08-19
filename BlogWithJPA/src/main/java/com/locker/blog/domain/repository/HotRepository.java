package com.locker.blog.domain.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Setter
@Getter @ToString
public class HotRepository {
    private Long repoId;
    private Long usrId;
    private String name;
    private String repoName;
    private Long Likes;
}
