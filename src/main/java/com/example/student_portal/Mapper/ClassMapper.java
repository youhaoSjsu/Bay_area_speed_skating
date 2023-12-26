package com.example.student_portal.Mapper;

import com.example.student_portal.apiModule.CancelClassRequest;
import com.example.student_portal.module.HpBoard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassMapper {
    @Select("SELECT class_id FROM student_portal.classes where class_name = #{className}")
    int getClassIdByClassName(@Param("className") String className);

    @Insert("Insert into student_portal.classcanceled (class_id,Date,reason) values (#{class_id},#{date},#{reason}); ")
    int createACancel(CancelClassRequest cr);





}
