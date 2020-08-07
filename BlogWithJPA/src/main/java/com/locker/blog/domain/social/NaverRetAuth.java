package com.locker.blog.domain.social;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NaverRetAuth {
    private String access_token;
    private String token_type;
    private String expires_in;
}
