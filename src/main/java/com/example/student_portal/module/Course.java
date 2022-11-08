package com.example.student_portal.module;

import java.sql.Time;
import java.time.DayOfWeek;

public class Course {
    private int c_id;
    private String location;
    private String name;
    private double price;

    private String sTime;


    private Time c_time;
    private DayOfWeek c_day;
    private String instructor;
    private int nc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Course() {
    }

    public Course(int c_id) {
        this.c_id = c_id;
    }

    public Course(int c_id, String teacher, String location, String time,String name) {
        this.c_id = c_id;
        this.instructor = teacher;
        this.location = location;
        this.sTime = time;
        this.name = name;

    }



    public Course(int id, int nc, double price, Time time, DayOfWeek day, String teacher,String location,String name)
    {
        c_id = id;
        this.nc = nc;
        this.price = price;
        this.c_time = time;
        this.c_day = day;
        this.instructor = teacher;
        this.name = name;
        this.location = location;
    }


    public int getNc() {
        return nc;
    }

    public void setNc(int nc) {
        this.nc = nc;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public DayOfWeek getC_day() {
        return c_day;
    }

    public void setC_day(DayOfWeek c_day) {
        this.c_day = c_day;
    }

    public Time getC_time() {
        return c_time;
    }

    public void setC_time(Time c_time) {
        this.c_time = c_time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }


}
