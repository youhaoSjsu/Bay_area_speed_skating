package com.example.student_portal.ServiceImpl;

import com.example.student_portal.Mapper.CourseMapper;
import com.example.student_portal.Service.CourseService;
import com.example.student_portal.module.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }


    @Override
    public Course getCourseByName(String name) {
        return this.courseMapper.getCourseByName(name);
    }

    @Override
    public Course getCourseById(int classId) {
        return courseMapper.getCourseById(classId);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseMapper.getAllCourses();
    }

    @Override
    public int insertCourse(Course classInfo) {
        return courseMapper.insertCourse(classInfo);
    }

    @Override
    public int updateCourse(Course classInfo) {
        return courseMapper.updateCourse(classInfo);
    }

    @Override
    public int deleteCourse(int classId) {
        return courseMapper.deleteCourse(classId);
    }


}
