package com.pigrz.service;

import com.pigrz.dao.RepairItemMapper;
import com.pigrz.pojo.RepairItem;
import com.pigrz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairItemServiceImpl implements RepairItemService{

    @Autowired
    RepairItemMapper repairItemMapper;


    @Override
    public List<RepairItem> getAllRepairItem() {
        return repairItemMapper.getAllRepairItem();
    }

    @Override
    public List<RepairItem> getRepairItemByType(String type) {
        if(type==null) {
            return new ArrayList<>();
        }
        else {
            return repairItemMapper.getRepairItemByType(type);
        }
    }

    @Override
    public List<RepairItem> getRepairItemByName(String name) {
        if(name==null) {
            return new ArrayList<>();
        }
        else {
            return repairItemMapper.getRepairItemByName(name);
        }
    }

    @Override
    public RepairItem getRepairItemByUniqueName(String name) {
        if(name==null) {
            return null;
        }
        else {
            return repairItemMapper.getRepairItemByUniqueName(name);
        }
    }

    @Override
    public List<String> getTypeList() {
        return repairItemMapper.getTypeList();
    }

    @Override
    public boolean addRepairItem(RepairItem repairItem) {
        if(repairItem==null) {
            return false;
        }
        else {
            return repairItemMapper.addRepairItem(repairItem) > 0;
        }
    }

    @Override
    public boolean updateRepairItem(RepairItem repairItem) {
        if(repairItem==null) {
            return false;
        }
        else {
            return repairItemMapper.updateRepairItem(repairItem) > 0;
        }
    }

    @Override
    public boolean deleteRepairItem(String id) {
        if (StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else {
            try {
                System.out.println(id);
                int i = Integer.parseInt(id);
                return repairItemMapper.deleteRepairItem(i) > 0;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("删除维修项目时输入id错误非法");
                return false;
            }
        }
    }
}
