package com.locker.blog.domain.repository;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GithubRepository {
    private Long id;
    private String node_id;
    private String name;
    private String full_name;
    private Boolean PRIVATE;
    private Owner owner;

    @Getter @Setter @ToString
    public class Owner {
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

    private String html_url;
    private String description;
    private Boolean fork;
    private String url;
    private String forks_url;
    private String keys_url;
    private String collaborators_url;
    private String teams_url;
    private String hooks_url;
    private String issue_events_url;
    private String events_url;
    private String assignees_url;
    private String branches_url;
    private String tags_url;
    private String blobs_url;
    private String git_tags_url;
    private String git_refs_url;
    private String trees_url;
    private String statuses_url;
    private String languages_url;
    private String stargazers_url;
    private String contributors_url;
    private String subscribers_url;
    private String subscription_url;
    private String commits_url;
    private String git_commits_url;
    private String comments_url;
    private String issue_comment_url;
    private String contents_url;
    private String compare_url;
    private String merges_url;
    private String archive_url;
    private String downloads_url;
    private String issues_url;
    private String pulls_url;
    private String milestones_url;
    private String notifications_url;
    private String labels_url;
    private String releases_url;
    private String deployments_url;
    private String created_at;
    private String updated_at;
    private String pushed_at;
    private String git_url;
    private String ssh_url;
    private String clone_url;
    private String svn_url;
    private String homepage;
    private Integer size;
    private Integer stargazers_count;
    private Integer watchers_count;
    private String language;
    private Boolean has_issues;
    private Boolean has_projects;
    private Boolean has_downloads;
    private Boolean has_wiki;
    private Boolean has_pages;
    private Integer forks_count;
    private String mirror_url;
    private Boolean archived;
    private Boolean disabled;
    private Integer open_issues_count;
    private String license;
    private Integer forks;
    private Integer open_issues;
    private Integer watchers;
    private String default_branch;
}
