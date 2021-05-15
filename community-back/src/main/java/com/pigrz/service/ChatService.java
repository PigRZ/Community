package com.pigrz.service;

import com.pigrz.pojo.ChatMessage;

import java.util.List;

public interface ChatService {
    List<ChatMessage> getChatMessageList();
    boolean addUserImageMessage(ChatMessage message);

    boolean addUserTextMessage(ChatMessage chatMessage);
    boolean addAdminImageMessage(ChatMessage message);
    boolean addAdminTextMessage(ChatMessage chatMessage);
}
