package com.example.student_portal.controller;

import com.example.student_portal.Service.ClassService;
import com.example.student_portal.apiModule.CancelClassRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private  ClassService classService;
    @Autowired
    public ClassController(ClassService classService)
    {
        this.classService =classService;
    }
//apis:

    @PostMapping("/api/recordACancel")
    public ResponseEntity<String> recordACancel(@RequestBody CancelClassRequest request)
    {
        try{
            //get the id;

            int id = classService.getClassIdByClassName(request.getClassName());
            request.setClass_id(id);
            String sql = "INSERT INTO `student_portal`.`classcanceled` (`class_id`, `Date`, `reason`) VALUES (?, ?, ?); ";
            int result = jdbcTemplate.update(sql,request.getClass_id(),request.getDate(),request.getReason());
            if(result == 1){
                HttpHeaders headers =new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);
                return  new ResponseEntity<>("class cancellation record",headers,HttpStatus.OK);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("nothing changed");


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }




}
