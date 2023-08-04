package com.example.student_portal.module;

import java.util.Date;

public class TrailClassAppRequest {
    private String applicant;
    private String email;
    private String number;
    private String class_id;

    private String date;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

// Add getters and setters for the fields

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getTrailEmail() {
        return email;
    }

    public void setTrailEmail(String trailEmail) {
        this.email = email;
    }

    public String getTrailNumber() {
        return number;
    }

    public void setTrailNumber(String trailNumber) {
        this.number = trailNumber;
    }
}
