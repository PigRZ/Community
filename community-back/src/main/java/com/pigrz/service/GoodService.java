package com.pigrz.service;

import com.pigrz.pojo.Good;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GoodService {


    //商品管理
    List<Good> getAllGood();
    List<Good> getGoodByType(String type);
    List<Good> getGoodByName(String name);
    Good getGoodByUniqueName(String name);
    boolean addGood(Good good);
    boolean updateGood(Good good);
    boolean deleteGood(String id);
    String getGoodImagePath(int id);
    List<Map<String,String>> getTypeList();
}
