package com.pigrz.dao;

import com.pigrz.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAllUser();
    List<User> getUserByNameOrTelephone(String nameOrTelephone);
    User getUserById(int id);
    List<User> getUserBySectionAndHealth(String section,String health);
    List<User> getUserBySection(String section);
    List<User> getUserByHealth(String health);
    User getUserByTelephone(String telephone);
    int updatePhoto(int id,String photo);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
    int changePwd(int id, String oldPwd, String newPwd);
    boolean updateUserHealth(int id, String health);
}