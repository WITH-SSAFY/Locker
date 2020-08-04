package com.locker.blog.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class KakaoProfile {
    private Long id;
    private String status;
    private String msg_blocked;
    private String registered_at;
    private String nickname;
    private String thumbnail_image;
    private String profile_image;
}