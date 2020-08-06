package com.locker.blog.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GithubProfile {
    private String login;
    private Long id;
    private String node_id;
    private String avatar_url;
    private String gravatar_id;
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
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private String twitter_username;
    private Long public_repos;
    private Long public_gists;
    private Long followers;
    private Long following;
    private String created_at;
    private String updated_at;
    private Long private_gists;
    private Long total_private_repos;
    private Long owned_private_repos;
    private Long disk_usage;
    private Long collaborators;
    private Boolean dtwo_factor_authentication;
    private Plan plan;

    @Getter @Setter @ToString
    private static class Plan {
        private String name;
        private Long space;
        private Long collaborators;
        private Long private_repos;
    }

}
