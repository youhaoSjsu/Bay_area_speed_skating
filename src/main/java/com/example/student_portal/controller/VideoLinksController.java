package com.example.student_portal.controller;

import com.example.student_portal.Service.VideoLinksService;
import com.example.student_portal.ServiceImpl.VideoLinksServiceImpl;
import com.example.student_portal.module.HpBoard;
import com.example.student_portal.module.SqlClass;
import com.example.student_portal.module.VideoLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoLinksController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private VideoLinksService videoLinksService;
    @Autowired
    public VideoLinksController(VideoLinksService videoLinksService)
    {
        this.videoLinksService = videoLinksService;
    }
    @PostMapping("/api/getVideoLinks")
    public ResponseEntity<VideoLinks[]> getVideoLinks()
    {
        List<VideoLinks> l = videoLinksService.findAllVideoLinks();
        VideoLinks [] vArr = l.toArray(new VideoLinks[l.size()]);
        return ResponseEntity.ok(vArr);
    }
    @PostMapping("/api/updateVideoLinks")
    public int updateVideoLinks(@RequestBody VideoLinks videoLinks)
    {
     int result = videoLinksService.updateVideoLinks(videoLinks);
     return result;
    }
    @PostMapping("/api/addVideoLinks")
    int addVideoLinks(@RequestBody VideoLinks videoLinks)
    {
        SqlClass sqlClass = new SqlClass(jdbcTemplate);
        videoLinks.setId(sqlClass.idGeneration(5));
        return videoLinksService.insertVideoLinks(videoLinks);
    }
    @PostMapping("/api/removeVideoLinks")
    public int deleteVideoLinks(@RequestBody VideoLinks videoLinks)
    {
        return videoLinksService.deleteVideoLinks(videoLinks);
    }
}
