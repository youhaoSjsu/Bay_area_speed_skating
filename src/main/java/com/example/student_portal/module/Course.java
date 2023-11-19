package com.example.student_portal.module;

import java.sql.Time;
import java.time.DayOfWeek;

public class Course {

    protected int class_id;

    public int capacity;
    public int enrollment;
    private String location;

    private int classCount;

    public String endDate;

    public String mode_of_instruction;
    public String time;
    private String class_name;
    private double price;

    private String startDate;

    private int showEnable;

    private String description;

    private int minStudent;
    private String status;

    public int getMinStudent() {
        return minStudent;
    }

    public void setMinStudent(int minStudent) {
        this.minStudent = minStudent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode_of_instruction() {
        return mode_of_instruction;
    }

    public void setMode_of_instruction(String mode_of_instruction) {
        this.mode_of_instruction = mode_of_instruction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public int getShowEnable() {
        return showEnable;
    }

    public void setShowEnable(int showEnable) {
        this.showEnable = showEnable;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(int enrollment) {
        this.enrollment = enrollment;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    private Time c_time;
    private DayOfWeek c_day;
    private String teacher;



    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
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
        this.class_id = c_id;
    }

    public Course(int c_id, String teacher, String location, String time,String name,double price) {
        this.class_id = c_id;
        this.teacher = teacher;
        this.location = location;
        this.time = time;
        this.class_name = name;
        this.price = price;

    }
    public Course(int c_id, String teacher, String location, String time,String name,double price,int showStatus) {
        this.class_id = c_id;
        this.teacher = teacher;
        this.location = location;
        this.time = time;
        this.class_name = name;
        this.price = price;
        this.showEnable = showStatus;
    }





    public Course(int id, int nc, double price, Time time, DayOfWeek day, String teacher,String location,String name)
    {
        class_id = id;
        this.classCount = nc;
        this.price = price;
        this.c_time = time;
        this.c_day = day;
        this.teacher = teacher;
        this.class_name = name;
        this.location = location;
    }




    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capcity) {
        this.capacity = capcity;
    }

    public int getClassCount() {
        return classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
