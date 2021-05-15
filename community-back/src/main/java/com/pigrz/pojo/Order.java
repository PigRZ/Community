package com.pigrz.pojo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private int userid;
    private int goodid;
    private String imagepath;
    private String useraddress;
    private String username;
    private String goodname;
    private int count;
    private Date date;
    private String done;
}
