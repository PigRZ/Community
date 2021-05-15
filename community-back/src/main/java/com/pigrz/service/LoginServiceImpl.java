package com.pigrz.service;

import com.pigrz.dao.AdminMapper;
import com.pigrz.dao.UserMapper;
import com.pigrz.pojo.Admin;
import com.pigrz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public User userLogin(String telephone,String password) {
        User user=userMapper.getUserByTelephone(telephone);
        if(user!=null&&password.equals(user.getPassword()))
            return user;
        else
            return null;
    }

    @Override
    public Admin adminLoin(String account,String password) {
        Admin admin=adminMapper.getAdminByAccount(account);
        if(admin!=null&&password.equals(admin.getPassword()))
            return admin;
        else
            return null;
    }
}
