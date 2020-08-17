package com.locker.blog.domain.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GithubCompactRepo {
    private String name;
    private String repoName;
    private String repoUrl;
}
