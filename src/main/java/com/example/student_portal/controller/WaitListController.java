package com.example.student_portal.controller;

import com.example.student_portal.ServiceImpl.WaitListService;
import com.example.student_portal.module.WaitList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class WaitListController {

    private final WaitListService waitListService;

    @Autowired
    public WaitListController(WaitListService waitListService) {
        this.waitListService = waitListService;
    }
    //testing
    @GetMapping("/api/saveWaitListRow")
    public String saveWaitListRow(@RequestBody WaitList waitList){

        //imitation
        Date current = new Date();
        //WaitList waitListTest = new WaitList(2931,"mike","email","4558788786",new java.sql.Date(current.getTime()),1);
        int result = waitListService.saveWaitListRow(waitList);
        if(result==1)
        {
            return "ok";
        }else {
            return "failed";
        }

    }

}
