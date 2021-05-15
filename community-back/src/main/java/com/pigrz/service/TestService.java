package com.pigrz.service;

import com.pigrz.pojo.Admin;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TestService {
    List<Admin> getAllAdmin();
}
