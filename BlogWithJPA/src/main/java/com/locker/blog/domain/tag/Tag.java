package com.locker.blog.domain.tag;

public class Tag {
    private Long tagid;
    private String tagname;

    public Long getTagid() {
        return tagid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
}
