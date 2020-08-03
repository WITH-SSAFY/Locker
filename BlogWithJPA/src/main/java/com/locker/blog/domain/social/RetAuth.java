package com.locker.blog.domain.social;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RetAuth {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}