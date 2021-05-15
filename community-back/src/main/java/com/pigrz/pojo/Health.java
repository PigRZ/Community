package com.pigrz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Health {
    private int id;
    private int userid;
    private double temperature;
    private String isUncomfortable;
    private String comeFromDangerous;
    private String detail;
    private Timestamp time;
}
