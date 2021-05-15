package com.pigrz.service;

import com.pigrz.pojo.UserPost;

import java.util.List;

public interface UserPostService {
    //
    //用户发帖管理
    List<UserPost> getAllUserPost();
    List<UserPost> getAllUserPostByUserid(int userid);
    boolean addUserPost(UserPost userPost);
    boolean deleteUserPost(String id);
    boolean updateUserPost(UserPost userPost);
    UserPost getUserPostById(int id);

}
