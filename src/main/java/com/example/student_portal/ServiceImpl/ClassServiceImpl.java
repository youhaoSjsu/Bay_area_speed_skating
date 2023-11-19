package com.example.student_portal.ServiceImpl;

import com.example.student_portal.Mapper.ClassMapper;
import com.example.student_portal.Mapper.HpBoardMapper;
import com.example.student_portal.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService {
    private final ClassMapper classMapper;
    @Autowired
    public ClassServiceImpl(ClassMapper classMapper){
        this.classMapper= classMapper;

    }

    @Override
    public int getClassIdByClassName(String className){
        return classMapper.getClassIdByClassName(className);
    }
}
