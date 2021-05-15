package com.pigrz.service;

import com.pigrz.dao.AdminMapper;
import com.pigrz.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private  AdminMapper mapper;

    @Override
    public List<Admin> getAllAdmin() {
        List<Admin> admins = mapper.getAllAdmin();
        return admins;
    }
}
