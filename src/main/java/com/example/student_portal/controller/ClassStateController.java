package com.example.student_portal.controller;

import com.example.student_portal.Service.ClassService;
import com.example.student_portal.Service.ClassStateService;
import com.example.student_portal.module.ClassState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClassStateController {

    private final ClassStateService stateService;
    @Autowired
    public ClassStateController(ClassStateService stateService) {
        this.stateService = stateService;
    }



    @PostMapping("/api/saveState")
    public int saveClassState(@RequestBody ClassState state)
    {
        if(stateService.addClassState(state) == 1)
        {
            return 1;
        }else{
            return -1;
        }
    }

    @PostMapping("/api/deleteClassState")
    public int deleteClassState(@RequestBody int class_id)
    {
        if(stateService.deleteAState(class_id) == 1)
        {
            return 1;
        }else{
            return -1;
        }
    }
    @PostMapping("/api/updateClassState")
    public int updateClassState(@RequestBody ClassState state)
    {
        if(stateService.updateClassState(state) == 1)
        {
            return 1;
        }else{
            return -1;
        }
    }
    @PostMapping("/api/selectAClassState")
    public ResponseEntity<ClassState> selectAclassState(@RequestBody int class_id )
    {
        ClassState state = stateService.selectAState(class_id);
        if(state!=null && state.getClass_id()==class_id)
        {
            return ResponseEntity.ok(state);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/api/findAllClassState")
    public ResponseEntity<ClassState[]> selectAClassState()
    {
    try{
        List<ClassState> lcs = stateService.findAllState();
        ClassState[] cArry = lcs.toArray(new ClassState[lcs.size()]);
        return  ResponseEntity.ok(cArry);
    }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }





}
