package com.pigrz.service;

import com.pigrz.pojo.RepairItem;

import java.util.List;

public interface RepairItemService {

    //维修项目管理
    List<RepairItem> getAllRepairItem();
    List<RepairItem> getRepairItemByType(String type);
    List<RepairItem> getRepairItemByName(String name);
    RepairItem getRepairItemByUniqueName(String name);
    List<String> getTypeList();
    boolean addRepairItem(RepairItem repairItem);
    boolean updateRepairItem(RepairItem repairItem);
    boolean deleteRepairItem(String id);
}
