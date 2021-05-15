package com.pigrz.service;

import com.pigrz.dao.CommentMapper;
import com.pigrz.pojo.Comment;
import com.pigrz.pojo.User;
import com.pigrz.util.Content;
import com.pigrz.util.FileUtil;
import com.pigrz.util.StringUtil;
import com.pigrz.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserService userService;

    @Override
    public List<Comment> getCommentByUserpostid(int userpostid) {
        List<Comment> commentList = commentMapper.getCommentByUserpostid(userpostid);
        for(Comment comment:commentList){
            User user= userService.getUserById(comment.getUserid());
            comment.setUserdir(Content.getUsrPhotoDir(user));
            comment.setUsername(user.getName());
            comment.setUserphoto(user.getPhoto());
            comment.setDir(Content.COMMENT_IMG_PATH);
        }
        return commentList;
    }

    @Override
    public boolean addComment(Comment comment) {

        Timestamp time=  TimeUtil.getCurTime();
        comment.setTime(time);
        if(!StringUtil.nullOrEmpty(comment.getImage())){
            FileUtil.moveFile(Content.TEMP_IMG_PATH,Content.COMMENT_IMG_PATH,comment.getImage());
        }
        return commentMapper.addComment(comment)>0;
    }

    @Override
    public boolean deleteCommentByUserpostid(int userpostid) {
        return commentMapper.deleteCommentByUserpostid(userpostid)>0;
    }
}
