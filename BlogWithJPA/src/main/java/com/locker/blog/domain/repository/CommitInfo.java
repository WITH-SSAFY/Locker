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
    @SerializedName(value = "node_id")
    private String nodeId;
    private Commit commit;

    @Getter @Setter @ToString
    public class Commit {
        private UserForm author;
        private UserForm committer;
        private String message;
        private Tree tree;
        private String url;
        @SerializedName(value = "comment_count")
        private String CommentCnt;
        private Verification verification;

        @Getter @Setter @ToString
        public class UserFrom {
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
    @SerializedName(value = "html_url")
    private String htmlUrl;
    @SerializedName(value = "comments_url")
    private String commentsUrl;
    private UserForm author;
    private UserForm committer;
    private List<Parents> parents;

    public class UserForm {
        private String login;
        private Long id;
        @SerializedName(value = "node_id")
        private String nodeId;
        @SerializedName(value = "avatar_url")
        private String avatarUrl;
        @SerializedName(value = "gravatar_id")
        private String gravatarId;
        private String url;
        @SerializedName(value = "html_url")
        private String htmlUrl;
        @SerializedName(value = "followers_url")
        private String followersUrl;
        @SerializedName(value = "following_url")
        private String followingUrl;
        @SerializedName(value = "gists_url")
        private String gistsUrl;
        @SerializedName(value = "starred_url")
        private String starredUrl;
        @SerializedName(value = "subscriptions_url")
        private String subscriptionsUrl;
        @SerializedName(value = "organizations_url")
        private String organizationsUrl;
        @SerializedName(value = "repos_url")
        private String reposUrl;
        @SerializedName(value = "events_url")
        private String eventsUrl;
        @SerializedName(value = "received_events_url")
        private String receivedEventUrl;
        private String type;
        @SerializedName(value = "site_admin")
        private Boolean siteAdmin;
    }

    @Setter @Getter @ToString
    public class Parents {
        private String sha;
        private String url;
        @SerializedName(value = "html_url")
        private String htmlUrl;
    }
}
