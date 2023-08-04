package com.example.student_portal.Mapper;

import com.example.student_portal.module.HpBoard;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HpBoardMapper {
    @Select("SELECT * FROM student_portal.board")
    List<HpBoard> findAllHpBoard();
    @Insert("Insert into student_portal.board (id,name,type,number,description,imageLinks) values (#{id},#{name},#{type},#{number},#{description},#{imageLinks}); ")
    int addHpBoard(HpBoard hpBoard);

    @Update("update student_portal.board set name=#{name},type=#{type}, number=#{number},description = #{description},imageLinks=#{imageLinks} where id = #{id}")
    int updateHpBoard(HpBoard hpBoard);

    @Delete("Delete from student_portal.board where id = #{id};")
    int deleteHpBoard(int id);


}
