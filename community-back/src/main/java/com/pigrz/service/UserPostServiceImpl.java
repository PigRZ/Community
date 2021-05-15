package com.pigrz.service;

import com.pigrz.dao.CommentMapper;
import com.pigrz.dao.UserPostMapper;
import com.pigrz.pojo.UserPost;
import com.pigrz.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserPostServiceImpl implements UserPostService {
    @Autowired
    UserPostMapper userPostMapper;
    @Autowired
    CommentMapper commentMapper;


    @Override
    public List<UserPost> getAllUserPost() {

        List<UserPost> userPosts = userPostMapper.getAllUserPost();
        for (UserPost userPost : userPosts) {
            if (StringUtil.nullOrEmpty(userPost.getUserphoto())) {
                userPost.setUserphoto("/image/default.png");
            } else {
                userPost.setUserphoto("/image/user/" + userPost.getUserid() + "/" + userPost.getUserphoto());
            }
        }
        return userPosts;
    }

    @Override
    public List<UserPost> getAllUserPostByUserid(int userid) {
        if (userid <= 0) {
            return new ArrayList<>();
        } else {
            return userPostMapper.getAllUserPostByUserid(userid);
        }
    }

    @Override
    public boolean addUserPost(UserPost userPost) {
        if (userPost == null) {
            return false;
        } else {
            Timestamp time = TimeUtil.getCurTime();
            userPost.setTime(time);
            StringBuilder img=new StringBuilder();
            List<String> images = userPost.getImages();
            for (int i = 0; i < images.size(); i++) {
                String image=images.get(i);
                int index=image.lastIndexOf('/');
                image=image.substring(index+1);
                FileUtil.moveFile(Content.TEMP_IMG_PATH,Content.getUsrPostDir(userPost),image);
                img.append(image);
                if(i!=images.size()-1){
                    img.append(";");
                }
            }
//            FileUtil.deleteDir(Content.TEMP_IMG_PATH);
            userPost.setImage(img.toString());
            return userPostMapper.addUserPost(userPost) > 0;
        }
    }

    @Override
    public boolean deleteUserPost(String id) {
        if (StringUtil.nullOrEmpty(id)) {
            return false;
        } else {
            try {
                int i = Integer.parseInt(id);
                commentMapper.deleteCommentByUserpostid(i);
                return userPostMapper.deleteUserPost(i) > 0;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("删除帖子时id不合法" + id);
                return false;
            }
        }
    }

    @Override
    public boolean updateUserPost(UserPost userPost) {
        if (userPost == null) {
            return false;
        } else {
            return userPostMapper.updateUserPost(userPost) > 0;
        }
    }

    @Override
    public UserPost getUserPostById(int id) {
        if (id<=0) {
            return null;
        } else {
            try {
                UserPost userPost = userPostMapper.getUserPostById(id);
                if (StringUtil.nullOrEmpty(userPost.getUserphoto())) {
                    userPost.setUserphoto("/image/default.png");
                } else {
                    userPost.setDir(Content.USERPOST_IMG_PATH);
                    userPost.setUserphoto("/image/user/" + userPost.getUserid() + "/" + userPost.getUserphoto());
                }
                String image = userPost.getImage();
                String[] images = image.split(";");
                userPost.setImages(new ArrayList<String >(Arrays.asList(images)));
                return userPost;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("查询帖子时id不合法" + id);
                return null;
            }
        }
    }
}
