package com.example.student_portal.controller;

import com.example.student_portal.module.Course;
import com.example.student_portal.module.SqlClass;
import com.example.student_portal.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @GetMapping("/api/getStuClass")
    public Course[] getStuClass(@RequestParam int stuId)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        System.out.println("Request! getStuClass");
        UserController uc= new UserController();
        List<Course> l = sqlClass.showtoke(1,stuId);
        return uc.convertListToArry(l);
    }
    @GetMapping("/api/getUserRole")
    public int getUserRole(@RequestParam int stuId)
    {
        System.out.println("Request! getUserRole");
        SqlClass sqlClass= new SqlClass(jdbcTemplate);
        String roleSql = "select role_id from has where user_id = "+stuId+";";

        int role_id = jdbcTemplate.queryForObject(roleSql, Integer.class);
        return role_id;
    }
}
