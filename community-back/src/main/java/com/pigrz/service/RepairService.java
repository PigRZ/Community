package com.pigrz.service;

import com.pigrz.pojo.Repair;

import java.sql.Date;
import java.util.List;

public interface RepairService {

    //居民报修管理
    List<Repair> getAllRepair();
    List<Repair> getRepairByDate(Date date);
    List<Repair> getRepairByUsername(String username);
    List<Repair> getRepairByRepairitemname(String repairitemname);
    List<Repair>  getRepairByUserid(int userid);
    boolean addRepair(Repair repair);
    boolean updateRepair(Repair repair);
    boolean completeRepair(int id);
    boolean deleteRepair(String id);
}
