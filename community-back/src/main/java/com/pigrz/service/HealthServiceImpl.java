package com.pigrz.service;

import com.pigrz.dao.HealthMapper;
import com.pigrz.pojo.Health;
import com.pigrz.pojo.User;
import com.pigrz.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class HealthServiceImpl implements HealthService{
    @Autowired
    HealthMapper healthMapper;
    @Autowired
    UserService userService;

    @Override
    public List<Health> getHealthByUserid(int userid) {
        if(userid<=0){
            return new ArrayList<>();
        }else{
            return healthMapper.getHealthByUserid(userid);
        }
    }

    @Override
    public boolean uploadHealth(Health health) {
        if(health==null){
            return false;
        }
        else{
            try {
                if(health.getTemperature()<35)
                    return false;
                Timestamp time =  TimeUtil.getCurTime();
                health.setTime(time);
                int userid = health.getUserid();
                if (health.getTemperature() > 37.3 || health.getIsUncomfortable().equals("是") || health.getComeFromDangerous().equals("是")) {
                    userService.updateUserHealth(userid, "红");
                } else {
                    userService.updateUserHealth(userid, "绿");
                }
                return healthMapper.addHealth(health) > 0;
            }catch (Exception e){
                return false;
            }
        }
    }
}
