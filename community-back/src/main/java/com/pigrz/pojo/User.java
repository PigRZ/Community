package com.pigrz.pojo;


import com.pigrz.util.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String sex;
    private String identity;
    private String telephone;
    private int section;
    private String cell;
    private String password;
    private String health;
    private String dir;
    private String photo;

}
