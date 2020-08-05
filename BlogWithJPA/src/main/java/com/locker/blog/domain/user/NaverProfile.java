package com.locker.blog.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class NaverProfile {
    private String resultcode;
    private String message;
    private Response response;

    @Getter @Setter @ToString
    public class Response {
        private String id;
        private String email;
        private String nickname;
        private String profile_image;
        private String age;
        private String gender;
        private String name;
        private String birthday;
    }
}
