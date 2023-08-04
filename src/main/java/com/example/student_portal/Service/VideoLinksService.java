package com.example.student_portal.Service;

import com.example.student_portal.module.HpBoard;
import com.example.student_portal.module.VideoLinks;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface VideoLinksService {

    List<VideoLinks> findAllVideoLinks();

    int updateVideoLinks(VideoLinks vl);

    int insertVideoLinks(VideoLinks vl);

    int deleteVideoLinks(VideoLinks vl);
}
