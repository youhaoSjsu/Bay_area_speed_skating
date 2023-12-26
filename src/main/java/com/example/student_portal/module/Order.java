package com.example.student_portal.module;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    String date;
    Date dDate;
    int user_id;
    int class_id;
    int orderId;

    double paid;

    List<String> dateList;

    public Order(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public Order(int orderId, int class_id, int user_id, String date, double paid) {
        this.user_id = user_id;
        this.class_id = class_id;
        this.orderId = orderId;
        this.date =date;
        this.paid =paid;


    }
    public void dDateSetter() throws ParseException {
        this.dDate = strToDate(date);
    }

    public Date strToDate(String d) throws ParseException {
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse(d);
        return date;
    }
}
