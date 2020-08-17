package com.locker.blog.domain.social;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter @ToString
public class Languages {
    @SerializedName("C++")
    private String cpp;
    @SerializedName("Java")
    private String java;
    @SerializedName("Vue")
    private String vue;
    @SerializedName("C")
    private String c;
    @SerializedName("Css")
    private String css;
    @SerializedName("Javascript")
    private String javascript;
    @SerializedName("HTML")
    private String html;
}
