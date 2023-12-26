package com.example.student_portal.module;

import java.util.Date;

public class WaitList {
    int class_id;
    String name;
    String email;
    String phone;
    Date dateW;
    int position;


    public WaitList() {
    }

    public WaitList(int class_id, String name, String email, String phone, Date date, int position) {
        this.class_id = class_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dateW = date;
        this.position = position;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateW() {
        return dateW;
    }

    public void setDateW(Date date) {
        this.dateW = date;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
