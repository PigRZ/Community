package com.pigrz.service;

import com.pigrz.dao.ChatMapper;
import com.pigrz.pojo.Admin;
import com.pigrz.pojo.ChatMessage;
import com.pigrz.pojo.User;
import com.pigrz.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    ChatMapper chatMapper;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @Override
    public List<ChatMessage> getChatMessageList() {
        List<ChatMessage> chatMessageList = chatMapper.getChatMessageList();
        for (ChatMessage chatMessage:chatMessageList){
            if(chatMessage.getSendType().equals("user")){
                int id=chatMessage.getUserid();
                User user=userService.getUserById(id);
                chatMessage.setDir(user.getDir());
                chatMessage.setUsername(user.getName());
                chatMessage.setPhoto(user.getPhoto());
            }else{
                int id=chatMessage.getAdminid();
                Admin admin=adminService.getAdminById(id);
                chatMessage.setAdminname(admin.getName());
                chatMessage.setPhoto("admin.jpeg");
            }
        }
        return chatMessageList;
    }

    @Override
    public boolean addUserImageMessage(ChatMessage chatMessage) {
        if(chatMessage==null)
            return false;
        else{
            chatMessage.setSendType("user");
            chatMessage.setMsgType("image");
            Timestamp time= TimeUtil.getCurTime();
            chatMessage.setTime(time);
            return chatMapper.addUserChatMessage(chatMessage)>0;
        }
    }

    @Override
    public boolean addUserTextMessage(ChatMessage chatMessage) {
        if(chatMessage==null)
            return false;
        else{
            chatMessage.setSendType("user");
            chatMessage.setMsgType("text");
            Timestamp time= TimeUtil.getCurTime();
            chatMessage.setTime(time);
            return chatMapper.addUserChatMessage(chatMessage)>0;
        }
    }
    @Override
    public boolean addAdminImageMessage(ChatMessage chatMessage) {
        if(chatMessage==null)
            return false;
        else{
            chatMessage.setSendType("admin");
            chatMessage.setMsgType("image");
            Timestamp time= TimeUtil.getCurTime();
            chatMessage.setTime(time);
            return chatMapper.addAdminChatMessage(chatMessage)>0;
        }
    }

    @Override
    public boolean addAdminTextMessage(ChatMessage chatMessage) {
        if(chatMessage==null)
            return false;
        else{
            chatMessage.setSendType("admin");
            chatMessage.setMsgType("text");
            Timestamp time= TimeUtil.getCurTime();
            chatMessage.setTime(time);
            return chatMapper.addAdminChatMessage(chatMessage)>0;
        }
    }

}
