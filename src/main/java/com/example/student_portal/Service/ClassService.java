package com.example.student_portal.Service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


public interface ClassService {
    int getClassIdByClassName(@Param("className") String className);
}
