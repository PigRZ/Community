package com.pigrz.service;

import com.pigrz.pojo.Health;

import java.util.List;

public interface HealthService {
    List<Health> getHealthByUserid(int userid);
    boolean uploadHealth(Health health);
}
