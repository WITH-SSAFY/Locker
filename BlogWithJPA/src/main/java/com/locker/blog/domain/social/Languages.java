package com.locker.blog.domain.social;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    @SerializedName("JavaScript")
    private String javascript;
    @SerializedName("HTML")
    private String html;
    @SerializedName("Shell")
    private String shell;
    @SerializedName("Python")
    private String python;
    @SerializedName("Ruby")
    private String ruby;
    @SerializedName("C#")
    private String cppp;
}
