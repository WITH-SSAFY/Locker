package com.locker.blog.domain.social;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class FbRetAuth {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String code;
}
