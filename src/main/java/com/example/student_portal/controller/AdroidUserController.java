package com.example.student_portal.controller;

import com.example.student_portal.Service.UserService;
import com.example.student_portal.module.MtUser;
import com.example.student_portal.module.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdroidUserController {
    private UserService userService;
    @Autowired
    public AdroidUserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/mtApi/getUsers")
    public MtUser mtGetUsers(@RequestParam String username)
    {
        MtUser u = userService.getSpecUser(username);
        return u;

    }

}
