package com.pigrz.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPost {
    private int id;
    private int userid;
    private String username;
    private String userphoto;
    private String title;
    private String content;
    private String image;
    private List<String> images;
    private String dir;
    private Timestamp time;
}
