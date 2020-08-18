package com.locker.blog.domain.repository;

import com.google.gson.annotations.SerializedName;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString
public class CommitInfo {
    private String sha;
    private String node_id;
    private Commit commit;

    @Getter @Setter @ToString
    public class Commit {
        private UserForm author;
        private UserForm committer;
        private String message;
        private Tree tree;
        private String url;
        private String comment_count;
        private Verification verification;

        @Getter @Setter @ToString
        public class UserForm {
            private String name;
            private String email;
            private String date;
        }

        @Getter @Setter @ToString
        public class Tree {
            private String sha;
            private String url;
        }

        @Getter @Setter @ToString
        public class Verification {
            private Boolean verified;
            private String reason;
            private String signature;
            private String payload;
        }
    }

    private String url;
    private String html_url;
    private String comments_url;
    private CUserForm author;
    private CUserForm committer;
    private List<Parents> parents;

    @Setter @Getter @ToString
    public class CUserForm {
        private String login;
        private Long id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private Boolean site_admin;
    }

    @Setter @Getter @ToString
    public class Parents {
        private String sha;
        private String url;
        @SerializedName(value = "html_url")
        private String html_url;
    }
}