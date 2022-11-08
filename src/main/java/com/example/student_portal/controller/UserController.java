package com.example.student_portal.controller;

import com.example.student_portal.module.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
//import mainCont;
@Controller()
public class UserController extends mainCont {

    //
    @Autowired
    public JdbcTemplate jdbcTemplate;
    List<Map<String,Object>> cl;

    @RequestMapping(value="/addClass",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> userRegister()
    {
        ModelAndView mv = new ModelAndView("userRegist.html");
        loadClass(0);
        return cl;

    }
    //this function use sql statement to load all classes of a school from database; and return a set of classes;
    //the school is defaulted right now
    public Set<Course> loadClass(int schoolCode)
    {
        Set<Course> sc = new LinkedHashSet<Course>();
        String sqlLoad = "select class_id, location,classes.time, course_name,users.username from classes join student_portal.is using (class_id) join student_portal.catalogue_courses using(course_abbreviation) join teach using(class_id) join users using(user_id) order by location;";
        //String sqlLoad = "select classes.class_id, catalogue_courses.course_abbreviation from classes join student_portal.is using(class_id) join catalogue_courses using(course_abbreviation)";
        List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
        Map<String,Object> courseMap =new HashMap<String,Object>();
        l =jdbcTemplate.queryForList(sqlLoad);
        cl = l;
        courseMap = l.get(0);





        return sc;


    }




}
