package com.example.student_portal.module;

import java.io.Serializable;

public class ClassState extends Course  {
    int minSize;
    int class_id;
    String state;

    String startAt;
    String endAt;

    public ClassState() {
    }

    public ClassState(int c_id, int minSize, int class_id, String state, String startAt, String endAt) {
        super(c_id);
        this.minSize = minSize;
        this.class_id = class_id;
        this.state = state;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }
}
