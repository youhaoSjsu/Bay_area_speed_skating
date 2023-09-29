package com.example.student_portal.ServiceImpl;

import com.example.student_portal.Mapper.VideoLinksMapper;
import com.example.student_portal.Service.VideoLinksService;
import com.example.student_portal.module.HpBoard;
import com.example.student_portal.module.VideoLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoLinksServiceImpl implements VideoLinksService {

    private final VideoLinksMapper videoLinksMapper;

    @Autowired
    public VideoLinksServiceImpl (VideoLinksMapper videoLinksMapper){
        this.videoLinksMapper = videoLinksMapper;
    }

    @Override
    public List<VideoLinks> findAllVideoLinks() {
        return videoLinksMapper.findAllVideoLinks();
    }

    @Override
    public int updateVideoLinks(VideoLinks vl) {
        return videoLinksMapper.updateVideoLinks(vl);
    }

    @Override
    public int insertVideoLinks(VideoLinks vl) {
        return videoLinksMapper.insertVideoLinks(vl);
    }

    @Override
    public int deleteVideoLinks(VideoLinks vl) {
        return videoLinksMapper.deleteVideoLinks(vl);
    }
}
