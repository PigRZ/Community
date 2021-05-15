package com.pigrz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int userid;
    private int userpostid;
    private String username;
    private String userphoto;
    private String userdir;
    private String content;
    private String dir;
    private String image;
    private Timestamp time;
}
