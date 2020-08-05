package com.locker.blog.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class FacebookProfile {
    private String id;
    private String name;
    private String email;
    private Picture picture;

    @Setter @Getter @ToString
    public class Picture {
        private Data data;

        @Setter @Getter @ToString
        public class Data {
            private Integer height;
            private Integer width;
            private Boolean is_silhouette;
            private String url;
        }
    }
}
