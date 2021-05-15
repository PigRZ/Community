package com.pigrz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repair {
    private int id;
    private int userid;
    private int repairitemid;
    private String username;
    private String repairitemname;
    private String useraddress;
    private String detail;
    private Date date;
    private String done;
}
