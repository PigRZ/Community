package com.pigrz.service;

import com.pigrz.pojo.AdminPost;

import java.util.List;

public interface AdminPostService {
    //管理员发帖管理
    List<AdminPost> getAllAdminPostByAdminid(String adminid);
    boolean addAdminPost(AdminPost adminPost);
    boolean deleteAdminPost(String id);
    boolean updateAdminPost(AdminPost adminPost);
    AdminPost getAdminPostById(String id);

    List<AdminPost> getAdminPostList();
}
