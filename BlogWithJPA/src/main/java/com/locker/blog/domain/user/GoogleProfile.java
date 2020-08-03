package com.locker.blog.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GoogleProfile {
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
