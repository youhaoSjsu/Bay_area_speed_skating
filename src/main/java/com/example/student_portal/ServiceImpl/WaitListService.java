package com.example.student_portal.ServiceImpl;

import com.example.student_portal.Mapper.WaitListMapper;
import com.example.student_portal.module.WaitList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitListService {
    private final WaitListMapper waitListMapper;

    @Autowired
    public WaitListService(WaitListMapper waitListMapper) {
        this.waitListMapper = waitListMapper;
    }
    public int saveWaitListRow(WaitList waitList){
        return waitListMapper.saveWaitListRow(waitList);
    }

    public List<WaitList> selectAllOfAClass(int class_id){
        return waitListMapper.selectAllOfAClass(class_id);
    }

    public int updateAWaitlistRow(WaitList waitList){
        return waitListMapper.updateAWaitlistRow(waitList);
    }

    public int deleteAWaitListRow(WaitList waitList){
        return waitListMapper.deleteAWaitListRow(waitList);
    }


}
