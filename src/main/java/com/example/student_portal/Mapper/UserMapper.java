package com.example.student_portal.Mapper;

import com.example.student_portal.module.MtUser;
import com.example.student_portal.module.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from student_portal.users where username = #{username}")
    MtUser getSpecUser(String username);
}
