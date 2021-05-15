package com.pigrz.dao;

import com.pigrz.pojo.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatMapper {
    List<ChatMessage> getChatMessageList();
    int addUserChatMessage(ChatMessage message);
    int addAdminChatMessage(ChatMessage message);
}
