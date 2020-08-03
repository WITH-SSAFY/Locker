package com.locker.blog.domain.comment;

import java.time.LocalDateTime;

public class Comment {
    private Long pid;
    private Long rid;
    private String replyId;
    private String replyName;
    private String replyText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getPid() {
        return pid;
    }

    public Long getRid() {
        return rid;
    }

    public String getReplyId() {
        return replyId;
    }

    public String getReplyName() {
        return replyName;
    }

    public String getReplyText() {
        return replyText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
