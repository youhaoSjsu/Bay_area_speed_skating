package com.example.student_portal.apiModule;

import java.util.Date;

public class CancelClassRequest {
    int class_id;
    Date date;

    String reason;

    public CancelClassRequest() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public CancelClassRequest(int class_id, Date date, String reason) {
        this.class_id = class_id;
        this.date = date;
        this.reason =reason;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
