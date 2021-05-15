package com.pigrz.service;

import com.pigrz.pojo.Good;
import com.pigrz.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    //居民管理
    List<User> getAllUser();
    List<User> getUserByNameOrTelephone(String nameOrTelephone);
    User getUserById(int id);
    List<User> getUserBySectionAndHealth(String section,String health);
    String changePwd(int id,String oldPwd,String newPwd);
    boolean deleteUser(String id);
    boolean updateUser(User user);
    boolean addUser(User user);
    boolean updateUserHealth(int id,String health);
    User getUserByTelephone(String telephone);
    String uploadPhoto(int id, MultipartFile file) throws IOException;
}
