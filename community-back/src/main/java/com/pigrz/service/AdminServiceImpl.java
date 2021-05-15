package com.pigrz.service;

import com.pigrz.dao.*;
import com.pigrz.pojo.*;
import com.pigrz.util.Content;
import com.pigrz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    AdminMapper adminMapper;
    @Override
    public boolean changePwd(Integer id, String oldPwd, String newPwd) {
        if (id > 0 && !StringUtil.nullOrEmpty(oldPwd) && !StringUtil.nullOrEmpty(newPwd)) {
            Admin admin = adminMapper.getAdminById(id);
            if (admin.getPassword().equals(oldPwd)) {
                return adminMapper.changePwd(id, oldPwd, newPwd) > 0;
            }
        }
        return false;
    }

    @Override
    public Admin getAdminById(int id) {
        return adminMapper.getAdminById(id);
    }
}
