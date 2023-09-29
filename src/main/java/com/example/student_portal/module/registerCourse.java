package com.example.student_portal.module;

import org.springframework.jdbc.core.JdbcTemplate;

public class registerCourse extends Course {
    private User customer;
    private int paidCount;
    private int registerCount;
    private boolean actived;
    private  int class_id;

    private String registerDate;

    //user's constructor
    public registerCourse(User aUser,int class_id, int registerCount,String date,boolean a)
    {
        customer = aUser;
        this.class_id = class_id;
        this.registerDate = date;
        this.registerCount =registerCount;
        this.actived = a;

    }




    public registerCourse()
    {

    }
}
