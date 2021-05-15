package com.pigrz.service;

import com.pigrz.pojo.*;

import java.sql.Date;
import java.util.List;

public interface AdminService {
    boolean changePwd(Integer id, String oldPwd, String newPwd);
    Admin getAdminById(int id);
}
