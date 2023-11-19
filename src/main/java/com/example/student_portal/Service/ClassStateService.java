package com.example.student_portal.Service;

import com.example.student_portal.module.ClassState;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ClassStateService {

    int addClassState(ClassState state);

    int updateClassState(ClassState state);
    ClassState selectAState(int class_id);

    int deleteAState(int class_id);

    List<ClassState> findAllState();
}
