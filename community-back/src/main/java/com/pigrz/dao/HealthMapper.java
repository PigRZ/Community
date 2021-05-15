package com.pigrz.dao;

import com.pigrz.pojo.Health;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HealthMapper {
    List<Health> getHealthByUserid(int userid);
    int addHealth(Health health);
}
