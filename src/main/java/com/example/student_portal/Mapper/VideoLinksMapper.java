package com.example.student_portal.Mapper;

import com.example.student_portal.module.HpBoard;
import com.example.student_portal.module.VideoLinks;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoLinksMapper {
    @Select("SELECT * FROM student_portal.videolinks;")
    List<VideoLinks> findAllVideoLinks();
    @Update("update videolinks set name=#{name}, description = #{description} where id = #{id};")
    int updateVideoLinks(VideoLinks vl);
    @Insert("insert into videolinks (id,name,description) value (#{id},#{name},#{description});")
    int insertVideoLinks(VideoLinks vl);
    @Delete("Delete from videolinks where id = #{id};")
    int deleteVideoLinks(VideoLinks vl);


}
