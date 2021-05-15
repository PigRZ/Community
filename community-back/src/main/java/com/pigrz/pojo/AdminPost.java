package com.pigrz.pojo;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPost {
    private int id;
    private int adminid;
    private String adminname;
    private String title;
    private String content;
    private Timestamp time;
}
