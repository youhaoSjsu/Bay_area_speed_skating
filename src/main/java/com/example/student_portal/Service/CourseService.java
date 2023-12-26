package com.example.student_portal.Service;

import com.example.student_portal.module.Course;

import java.util.List;

public interface CourseService {



    Course getCourseByName(String name);
    Course getCourseById(int classId);

    List<Course> getAllCourses();


    int insertCourse(Course classInfo);

    int updateCourse(Course classInfo);

    int deleteCourse(int classId);



}
