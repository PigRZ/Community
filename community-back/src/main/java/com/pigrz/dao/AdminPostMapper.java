package com.pigrz.dao;

import com.pigrz.pojo.Admin;
import com.pigrz.pojo.AdminPost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminPostMapper {
    List<AdminPost> getAllAdminPostByAdminid(int adminid);
    int addAdminPost(AdminPost adminPost);
    int deleteAdminPost(int id);
    int updateAdminPost(AdminPost adminPost);
    AdminPost getAdminPostById(int id);
    List<AdminPost> getAdminPostList();
}
