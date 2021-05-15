package com.pigrz.dao;

import com.pigrz.pojo.UserPost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserPostMapper {
    List<UserPost> getAllUserPost();
    List<UserPost> getAllUserPostByUserid(int userid);
    int addUserPost(UserPost userPost);
    int deleteUserPost(int id);
    int updateUserPost(UserPost userPost);
    UserPost getUserPostById(int id);
}
