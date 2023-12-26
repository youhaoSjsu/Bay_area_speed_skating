package com.example.student_portal.Mapper;

import com.example.student_portal.module.ClassState;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassStateMapper {
    @Insert("Insert into student_portal.classstate (class_id, minSize, state, startAt, endAt) VALUES (#{class_id},#{minSize},#{state}, #{startAt}, #{endAt}); ")
    int addClassState(ClassState state);

    @Update("update student_portal.classstate set minSize =#{minSize}, state=#{state},startAt=#{startAt}, endAt=#{endAt} where class_id = #{class_id};")
    int updateClassState(ClassState state);
    @Select("Select * student_portal.classstate where class_id = #{class_id};")
    ClassState selectAState(int class_id);

    @Delete("delete from student_portal.classstate where class_id =#{class_id};")
    int deleteAState(int class_id);

    @Select("SELECT class_id, minSize, state, startAt, endAt FROM student_portal.classstate")
    List<ClassState> findAllState();

}
