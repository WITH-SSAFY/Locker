package com.locker.blog.domain.social;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GithubRetAuth {
    private String access_token;
    private String token_type;
    private String scope;
}
