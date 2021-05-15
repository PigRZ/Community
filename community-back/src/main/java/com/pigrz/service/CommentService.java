package com.pigrz.service;

import com.pigrz.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentByUserpostid(int userpostid);
    boolean addComment(Comment comment);
    boolean deleteCommentByUserpostid(int userpostid);
}
