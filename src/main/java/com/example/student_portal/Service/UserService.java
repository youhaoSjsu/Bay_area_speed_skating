package com.example.student_portal.Service;

import com.example.student_portal.module.MtUser;
import com.example.student_portal.module.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
    MtUser getSpecUser(String username);


}
