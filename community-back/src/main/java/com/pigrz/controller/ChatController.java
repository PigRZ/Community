package com.pigrz.controller;

import com.pigrz.pojo.Admin;
import com.pigrz.pojo.User;
import com.pigrz.service.AdminService;
import com.pigrz.service.AdminServiceImpl;
import com.pigrz.service.UserService;
import com.pigrz.service.UserServiceImpl;
import com.pigrz.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@ServerEndpoint("/chat/{sendType}/{id}/{name}/{photo}")
public class ChatController {


    private static final Set<Session> sessions = new HashSet<>();
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "sendType") String sendType,@PathParam(value = "name") String name,@PathParam(value = "id") int id) {
        if(sendType.equals("admin")) {
            sessions.add(session);
//            sendTextMsg("管理员 [" + name + "]加入群聊");
            System.out.println("管理员 [" + name + "]加入群聊");
        }
        else if(sendType.equals("user")) {
            sessions.add(session);
//            sendTextMsg("居民 [" + name + "]加入群聊");
            System.out.println("居民 [" + name + "]加入群聊");
        }
    }

    @OnMessage
    public void OnMsg(String msg, @PathParam(value = "sendType") String sendType,@PathParam(value = "name") String name,@PathParam(value = "id") int id,@PathParam(value = "photo") String photo) {
        System.out.println(sendType+id+"发送"+msg);
        Timestamp time = TimeUtil.getCurTime();
        msg = sendType + "#" + id + "#" + name + "#" + photo + "#" + "text" + "#" + time.toString() + "#" + msg;
        sendTextMsg(msg);
    }
    @OnClose
    public void OnClose(Session session,  @PathParam(value = "sendType") String sendType,@PathParam(value = "name") String name,@PathParam(value = "id") int id) throws IOException {
        if(sendType.equals("admin")) {
            sessions.remove(session);
//            sendTextMsg("管理员 [" + name + "] 退出群聊");
            System.out.println("管理员 [" + name + "] 退出群聊");
        }
        else if(sendType.equals("user")) {
            sessions.remove(session);
//            sendTextMsg("居民 [" + name + "] 退出群聊");
            System.out.println("居民 [" + name+ "] 退出群聊");
        }
    }
    @OnError
    public void OnError(Throwable e) {
        e.printStackTrace();
    }
    private void sendTextMsg(String msg){
        try {
            for (Session session : sessions) {
                session.getBasicRemote().sendText(msg);
//            session.getAsyncRemote().sendText(msg);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
