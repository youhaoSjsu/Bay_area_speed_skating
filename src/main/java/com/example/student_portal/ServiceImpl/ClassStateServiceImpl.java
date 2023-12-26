package com.example.student_portal.ServiceImpl;

import com.example.student_portal.Mapper.ClassStateMapper;
import com.example.student_portal.Mapper.ClassStateMapper;
import com.example.student_portal.Mapper.HpBoardMapper;
import com.example.student_portal.Service.ClassStateService;
import com.example.student_portal.Service.ClassStateService;
import com.example.student_portal.module.ClassState;
import com.example.student_portal.module.ClassState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassStateServiceImpl implements ClassStateService {
    private final ClassStateMapper mapper;
    @Autowired
    public ClassStateServiceImpl(ClassStateMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int addClassState(ClassState state) {
        return mapper.addClassState(state);
    }

    @Override
    public int updateClassState(ClassState state) {
        return mapper.updateClassState(state);
    }

    @Override
    public ClassState selectAState(int class_id) {
        return mapper.selectAState(class_id);
    }

    @Override
    public int deleteAState(int class_id) {
        return mapper.deleteAState(class_id);
    }

    @Override
    public List<ClassState> findAllState() {
        return mapper.findAllState();
    }
}
