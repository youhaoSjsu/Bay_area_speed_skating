package com.example.student_portal.module;

public class classPersonDataRow {
    private int class_id;

    private String class_name;

    private boolean continuous;

    public boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public classPersonDataRow(int class_id, String class_name)

    {
        this.class_id = class_id;
        this.class_name =class_name;
    }
    private int user_id;
    private String app_date;

    private double price;

    private int orderCount;
    private String target_date;

    private int active;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPrice() {
        return price;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    private  String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public classPersonDataRow() {
    }

    public classPersonDataRow(int class_id, int user_id) {
        this.class_id = class_id;
        this.user_id = user_id;

    }

    public classPersonDataRow(int class_id, int user_id, String app_date, String target_date) {
        this.class_id = class_id;
        this.user_id = user_id;
        this.app_date = app_date;
        this.target_date = target_date;
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

    public String getApp_date() {
        return app_date;
    }

    public void setApp_date(String app_date) {
        this.app_date = app_date;
    }

    public String getTarget_date() {
        return target_date;
    }

    public void setTarget_date(String target_date) {
        this.target_date = target_date;
    }


}
