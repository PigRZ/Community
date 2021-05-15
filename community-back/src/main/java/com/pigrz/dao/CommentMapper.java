package com.pigrz.dao;

import com.pigrz.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    List<Comment> getCommentByUserpostid(int userpostid);
    int addComment(Comment comment);
    int deleteCommentByUserpostid(int userpostid);
}
