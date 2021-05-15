package com.pigrz.service;

import com.pigrz.dao.GoodMapper;
import com.pigrz.dao.UserMapper;
import com.pigrz.pojo.Good;
import com.pigrz.pojo.User;
import com.pigrz.util.Content;
import com.pigrz.util.ImgUtil;
import com.pigrz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    GoodMapper goodMapper;
    @Autowired
    UserMapper userMapper;



    @Override
    public List<User> getAllUser() {

        List<User> users = userMapper.getAllUser();
        for(User user:users){
            user.setDir(Content.getUsrPhotoDir(user));
        }
        return users;
    }

    @Override
    public List<User> getUserByNameOrTelephone(String nameOrTelephone) {
        List<User> users = userMapper.getUserByNameOrTelephone(nameOrTelephone);
        for(User user:users){
            user.setDir(Content.getUsrPhotoDir(user));
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        if(id<=0){
            return new User();
        }
        else{
            User user = userMapper.getUserById(id);
            user.setDir(Content.getUsrPhotoDir(user));
            return user;
        }
    }

    @Override
    public List<User> getUserBySectionAndHealth(String section, String health) {
        List<User> users =null;
        if (StringUtil.nullOrEmpty(section) && StringUtil.nullOrEmpty(health)) {
            users = userMapper.getAllUser();
        }
        else if (StringUtil.nullOrEmpty(section)) {
            users = userMapper.getUserByHealth(health);
        }
        else if (StringUtil.nullOrEmpty(health)) {
            users = userMapper.getUserBySection(section);
        }
        else {
            users = userMapper.getUserBySectionAndHealth(section, health);
        }
        for(User user:users){
            user.setDir(Content.getUsrPhotoDir(user));
        }
        return users;
    }

    @Override
    public String  changePwd(int id, String oldPwd, String newPwd) {
        if (id > 0 && !StringUtil.nullOrEmpty(oldPwd) && !StringUtil.nullOrEmpty(newPwd)) {
            User user = userMapper.getUserById(id);
            if (user.getPassword().equals(oldPwd)) {
                if (userMapper.changePwd(id, oldPwd, newPwd) > 0)
                    return Content.SUCCESS;
            }
        }
        return Content.FAIL;
    }

    @Override
    public boolean deleteUser(String id) {
        if (StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else {
            try {
                System.out.println(id);
                int i = Integer.parseInt(id);
                return userMapper.deleteUser(i) > 0;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("输入id错误非法");
                return false;
            }
        }
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null) {
            return false;
        }
        else {
            return userMapper.updateUser(user) > 0;
        }
    }

    @Override
    public boolean addUser(User user) {
        if(user==null) {
            return false;
        }
        else {
            user.setPassword("123456");
            user.setHealth("绿");
            return userMapper.addUser(user) > 0;
        }
    }

    @Override
    public boolean updateUserHealth(int id, String health) {
        if(id<=0||StringUtil.nullOrEmpty(health)){
            return false;
        }
        else{
            return userMapper.updateUserHealth(id,health);
        }
    }

    @Override
    public User getUserByTelephone(String telephone){
        if(StringUtil.nullOrEmpty(telephone)) {
            return null;
        }
        else {
            return userMapper.getUserByTelephone(telephone);
        }
    }

    @Override
    public String uploadPhoto(int id, MultipartFile file) throws IOException {
        String photo = ImgUtil.uploadAvatarHandler(Content.USER_PHOTO_PATH + id + "/", file,"default.png");
        userMapper.updatePhoto(id,photo);
        return photo;
    }
}
