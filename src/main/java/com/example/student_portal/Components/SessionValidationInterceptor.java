package com.example.student_portal.Components;

import com.example.student_portal.module.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionValidationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception{
        HttpSession session = request.getSession();
        User u =(User) session.getAttribute("currentA");
        return u.getId() > 0;
    }
}
