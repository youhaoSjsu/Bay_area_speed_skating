package com.example.student_portal.module;

public class absentData {

    int class_id;
    int user_id;
    String absentDate;
    String referDate;
    String makeUpDate;

    public absentData()
    {

    }

    public absentData(int class_id, int user_id, String absentDate, String referDate, String makeUpDate) {
        this.class_id = class_id;
        this.user_id = user_id;
        this.absentDate = absentDate;
        this.referDate = referDate;
        this.makeUpDate = makeUpDate;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAbsentDate() {
        return absentDate;
    }

    public void setAbsentDate(String absentDate) {
        this.absentDate = absentDate;
    }

    public String getReferDate() {
        return referDate;
    }

    public void setReferDate(String referDate) {
        this.referDate = referDate;
    }

    public String getMakeUpDate() {
        return makeUpDate;
    }

    public void setMakeUpDate(String makeUpDate) {
        this.makeUpDate = makeUpDate;
    }
}
