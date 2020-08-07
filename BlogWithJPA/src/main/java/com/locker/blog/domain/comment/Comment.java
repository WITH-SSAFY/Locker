package com.locker.blog.domain.comment;

import java.time.LocalDateTime;

public class Comment {
    private Long pid;
    private Long rid;
    private Long parentid;
    private int depth;
    private String replyemail;
    private String replynickname;
    private String replytext;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Long getPid() {
        return pid;
    }

    public Long getRid() {
        return rid;
    }

    public Long getParentid() {
        return parentid;
    }

    public int getDepth() {
        return depth;
    }

    public String getReplyemail() {
        return replyemail;
    }

    public String getReplynickname() {
        return replynickname;
    }

    public String getReplytext() {
        return replytext;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setReplyemail(String replyemail) {
        this.replyemail = replyemail;
    }

    public void setReplynickname(String replynickname) {
        this.replynickname = replynickname;
    }

    public void setReplytext(String replytext) {
        this.replytext = replytext;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
