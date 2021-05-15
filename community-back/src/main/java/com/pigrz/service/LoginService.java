package com.pigrz.service;

import com.pigrz.pojo.Admin;
import com.pigrz.pojo.User;

public interface LoginService {
    User userLogin(String telephone, String password);
    Admin adminLoin(String account, String password);
}
