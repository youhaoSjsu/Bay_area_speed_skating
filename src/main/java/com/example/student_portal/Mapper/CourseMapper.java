package com.example.student_portal.Mapper;

import com.example.student_portal.module.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Insert("INSERT INTO student_portal.classes (class_id, enrollment, capacity, mode_of_instruction, location, " +
            "time, price, classCount, startDate, endDate, class_name, teacher, showEnable, description) " +
            "VALUES (#{classId}, #{enrollment}, #{capacity}, #{modeOfInstruction}, #{location}, #{time}, #{price}, " +
            "#{classCount}, #{startDate}, #{endDate}, #{className}, #{teacher}, #{showEnable}, #{description})")
    int insertCourse(Course classInfo);

    @Select("SELECT * FROM student_portal.classes")
    List<Course> getAllCourses();

    @Update("UPDATE student_portal.classes SET enrollment = #{enrollment}, capacity = #{capacity}, " +
            "mode_of_instruction = #{modeOfInstruction}, location = #{location}, time = #{time}, price = #{price}, " +
            "classCount = #{classCount}, startDate = #{startDate}, endDate = #{endDate}, " +
            "class_name = #{className}, teacher = #{teacher}, showEnable = #{showEnable}, " +
            "description = #{description} WHERE class_id = #{classId}")
    int updateCourse(Course classInfo);

    @Delete("DELETE FROM student_portal.classes WHERE class_id = #{classId}")
    int deleteCourse(@Param("classId") int classId);


    @Select("SELECT * FROM student_portal.classes WHERE class_id = #{classId}")
    Course getCourseById(@Param("classId") int classId);

    @Select("SELECT * FROM student_portal.classes WHERE class_id = #{class_name}")
    Course getCourseByName(@Param("class_name") String class_name);

}
