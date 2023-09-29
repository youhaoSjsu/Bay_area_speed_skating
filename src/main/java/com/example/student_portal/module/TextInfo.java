package com.example.student_portal.module;

import java.security.PrivateKey;

public class TextInfo extends Award {

private  String type;
private  String videoLink;

    public TextInfo() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
