package com.example.student_portal.module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class classOfaCourse extends  Course{

    Date dateOfAbsent;
    String sDateOfAbsent;

    Date referDate;
    String sReferDate;
    int user_id;

    String comment;
    String md;

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public classOfaCourse (int class_id, int user_id, String sDateOfAbsent, String sReferDate, String comment) throws ParseException {
        this.c_id = class_id;
        this.user_id = user_id;
        this.sDateOfAbsent = sDateOfAbsent;
        this.sReferDate = sReferDate;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dateOfAbsent = dateFormat.parse(sDateOfAbsent);
        referDate = dateFormat.parse(sReferDate);
        this.comment = comment;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getsDateOfAbsent() {
        return sDateOfAbsent;
    }

    public void setsDateOfAbsent(String sDateOfAbsent) {
        this.sDateOfAbsent = sDateOfAbsent;
    }

    public String getsReferDate() {
        return sReferDate;
    }

    public void setsReferDate(String sReferDate) {
        this.sReferDate = sReferDate;
    }

    public Date getDateOfAbsent() {
        return dateOfAbsent;
    }

    public void setDateOfAbsent(Date dateOfAbsent) {
        this.dateOfAbsent = dateOfAbsent;
    }

    public Date getReferDate() {
        return referDate;
    }

    public void setReferDate(Date referDate) {
        this.referDate = referDate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
