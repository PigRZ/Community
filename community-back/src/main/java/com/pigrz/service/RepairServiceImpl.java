package com.pigrz.service;

import com.pigrz.dao.RepairMapper;
import com.pigrz.pojo.Repair;
import com.pigrz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepairServiceImpl implements RepairService{
    @Autowired
    RepairMapper repairMapper;

    @Override
    public List<Repair> getAllRepair() {
        return repairMapper.getAllRepair();
    }

    @Override
    public List<Repair> getRepairByDate(Date date) {
        if(date==null) {
            return new ArrayList<>();
        }
        else {
            return repairMapper.getRepairByDate(date);
        }
    }

    @Override
    public List<Repair> getRepairByUsername(String username) {
        if(username==null) {
            return new ArrayList<>();
        }
        else {
            return repairMapper.getRepairByUsername(username);
        }
    }

    @Override
    public List<Repair> getRepairByRepairitemname(String repairitemname) {
        if(repairitemname==null) {
            return new ArrayList<>();
        }
        else {
            return repairMapper.getRepairByRepairitemname(repairitemname);
        }
    }

    @Override
    public List<Repair> getRepairByUserid(int userid) {
        return repairMapper.getRepairByUserid(userid);
    }

    @Override
    public boolean addRepair(Repair repair) {
        if(repair==null) {
            return false;
        }
        else {
            repair.setDate(new Date(System.currentTimeMillis()));
            repair.setDone("否");
            return repairMapper.addRepair(repair)>0;
        }
    }

    @Override
    public boolean updateRepair(Repair repair) {
        if(repair==null) {
            return false;
        }
        else {
            return repairMapper.updateRepair(repair)>0;
        }
    }

    @Override
    public boolean completeRepair(int id) {
        if(id<=0) {
            return false;
        }
        else{
            int i= repairMapper.completeRepair(id);
            return i>0;
        }
    }

    @Override
    public boolean deleteRepair(String id) {
        if(StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else{
            try {
                int i=Integer.parseInt(id);
                return repairMapper.deleteRepair(i)>0;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("删除维修记录id："+id);
                return false;
            }
        }
    }
}
