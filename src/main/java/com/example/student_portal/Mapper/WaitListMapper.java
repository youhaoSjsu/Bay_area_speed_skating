package com.example.student_portal.Mapper;

import com.example.student_portal.module.WaitList;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WaitListMapper {
    @Insert("INSERT INTO student_portal.waitlist (class_id, name, email, phone, dateW, position) VALUES (#{class_id}, #{name}, #{email}, #{phone}, #{dateW}, #{position});")
    int saveWaitListRow(WaitList waitList);

    @Select("Select * from waitlist where class_id = #{class_id}")
    List<WaitList> selectAllOfAClass(int class_id);

    @Update("Update student.portal.waitlist set class_id = #{class_id},name = #{name}, email=#{email},phone = #{phone},dateW=#{dateW},position=#{position} where class_id=#{class_id} and name=#{name} and phone=#{phone}; ")
    int updateAWaitlistRow(WaitList waitList);

    @Delete("Delete from student_portal.waitlist where class_id=#{class_id} and name=#{name} and phone=#{phone};")
    int deleteAWaitListRow(WaitList waitList);

}
