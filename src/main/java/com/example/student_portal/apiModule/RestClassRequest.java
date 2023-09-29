package com.example.student_portal.apiModule;

import java.util.Date;

public class RestClassRequest {
    int class_id;
    Date start;
    int paidClasses;

    public RestClassRequest() {
    }

    public RestClassRequest(int class_id, Date start, int paidClasses) {
        this.class_id = class_id;
        this.start = start;
        this.paidClasses = paidClasses;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getPaidClasses() {
        return paidClasses;
    }

    public void setPaidClasses(int paidClasses) {
        this.paidClasses = paidClasses;
    }
}
