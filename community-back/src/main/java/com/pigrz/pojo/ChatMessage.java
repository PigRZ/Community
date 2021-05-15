package com.pigrz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private int id;
    private String sendType;
    private int userid;
    private String username;
    private String dir;
    private String photo;
    private int adminid;
    private String adminname;
    private String msgType;
    private String content;
    private String image;
    private Timestamp time;
}
