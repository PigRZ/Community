package com.pigrz.dao;

import com.pigrz.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    //获取所有管理员
    List<Admin> getAllAdmin();
    //根据id获取管理员
    Admin getAdminById(int id);
    //根据账号获取管理员
    Admin getAdminByAccount(String account);
    int changePwd(Integer id, String oldPwd, String newPwd);
}
