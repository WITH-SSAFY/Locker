package com.locker.blog.domain.tag;

public class Tag {
    private Long tagid;
    private String name;

    public Long getTagid() {
        return tagid;
    }

    public String getName() {
        return name;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
