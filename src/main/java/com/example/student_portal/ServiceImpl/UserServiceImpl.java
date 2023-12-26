package com.example.student_portal.ServiceImpl;

import com.example.student_portal.Mapper.UserMapper;
import com.example.student_portal.Service.UserService;
import com.example.student_portal.module.MtUser;
import com.example.student_portal.module.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public MtUser getSpecUser(String username) {
        return userMapper.getSpecUser(username);
    }
}
