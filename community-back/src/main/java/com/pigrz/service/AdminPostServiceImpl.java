package com.pigrz.service;

import com.pigrz.dao.AdminPostMapper;
import com.pigrz.pojo.AdminPost;
import com.pigrz.util.StringUtil;
import com.pigrz.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminPostServiceImpl implements AdminPostService{
    @Autowired
    AdminPostMapper adminPostMapper;

    @Override
    public List<AdminPost> getAllAdminPostByAdminid(String adminid) {
        if(StringUtil.nullOrEmpty(adminid)) {
            return new ArrayList<>();
        }
        else {
            try {
                int aid = Integer.parseInt(adminid);
                return adminPostMapper.getAllAdminPostByAdminid(aid);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("查询发帖记录时adminid错误" + adminid);
                return new ArrayList<>();
            }
        }
    }

    @Override
    public boolean addAdminPost(AdminPost adminPost) {
        if(adminPost==null) {
            return false;
        }
        else {
            Timestamp time=  TimeUtil.getCurTime();
            adminPost.setTime(time);
            return adminPostMapper.addAdminPost(adminPost)>0;
        }
    }

    @Override
    public boolean deleteAdminPost(String id) {
        if(StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else{
            try{
                int i=Integer.parseInt(id);
                return adminPostMapper.deleteAdminPost(i)>0;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("删除帖子时id不合法"+id);
                return false;
            }
        }
    }

    @Override
    public boolean updateAdminPost(AdminPost adminPost) {
        if(adminPost==null) {
            return false;
        }
        else{
            return adminPostMapper.updateAdminPost(adminPost)>0;
        }
    }

    @Override
    public AdminPost getAdminPostById(String id) {
        if(StringUtil.nullOrEmpty(id)) {
            return null;
        }
        else{
            try{
                int i=Integer.parseInt(id);
                return adminPostMapper.getAdminPostById(i);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("查询帖子时id不合法"+id);
                return null;
            }
        }
    }

    @Override
    public List<AdminPost> getAdminPostList() {
        return adminPostMapper.getAdminPostList();
    }
}
