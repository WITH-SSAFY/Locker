package com.locker.blog.domain.postlike;

public class Postlike {
    private Long likeid;
    private Long pid;
    private Long id;

    public Long getLikeid() {
        return likeid;
    }

    public Long getPid() {
        return pid;
    }

    public Long getId() {
        return id;
    }

    public void setLikeid(Long likeid) {
        this.likeid = likeid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
