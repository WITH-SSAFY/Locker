package com.locker.blog.domain.post;

import javax.persistence.*;
import java.time.LocalDateTime;

//paging
public class PagingPost {
    private Long pid;
    private String email;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long likes;
    private Long num;//일련번호

    public Long getPid() {
        return pid;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public Long getLikes() {
        return likes;
    }

    public Long getNum() {
        return num;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
