package com.example.student_portal.apiModule;

import java.util.Date;

public class RestClassRespond {

    int class_id;
    int restClasses;
    String endDate;

    String[] restClassDate;
    String [] allClasses;

//    public RestClassRespond(int class_id, int restClasses, Date endDate, Date[] restClassDate) {
//        this.class_id = class_id;
//        this.restClasses = restClasses;
//        this.endDate = endDate;
//        this.restClassDate = restClassDate;
//    }
    public RestClassRespond(int class_id, int restClasses, String endDate) {
        this.class_id = class_id;
        this.restClasses = restClasses;
        this.endDate = endDate;
        this.restClassDate = null;
    }

    public RestClassRespond() {
    }

    public RestClassRespond(int class_id, int restClasses, String endDate, String[] restClassDate, String [] allClasses) {
        this.class_id = class_id;
        this.restClasses = restClasses;
        this.endDate = endDate;
        this.restClassDate = restClassDate;
        this.allClasses = allClasses;
    }

    public String[] getRestClassDate() {
        return restClassDate;
    }

    public void setRestClassDate(String[] restClassDate) {
        this.restClassDate = restClassDate;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getRestClasses() {
        return restClasses;
    }

    public void setRestClasses(int restClasses) {
        this.restClasses = restClasses;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
