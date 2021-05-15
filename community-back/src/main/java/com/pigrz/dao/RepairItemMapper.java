package com.pigrz.dao;

import com.pigrz.pojo.RepairItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RepairItemMapper {

    /**
     * 获取所有维修项目的信息
     * @return 数据库中所有维修项目
     */
    List<RepairItem> getAllRepairItem();

    /**
     * 根据维修项目类型获取该类型的所有维修项目
     * @param type 维修项目类型
     * @return  该类型的所有维修项目
     */
    List<RepairItem> getRepairItemByType(String type);

    /**
     * 根据维修项目名称的关键词获取包含该名称的所有维修项目
     * @param name 维修项目全名所包含的名字
     * @return 包含该名字的所有维修项目
     */
    List<RepairItem> getRepairItemByName(String name);

    /**
     * 根据维修项目全名获取维修项目
     * @param name 维修项目的全名
     * @return 全名为name的所有维修项目
     */
    RepairItem getRepairItemByUniqueName(String name);

    List<String> getTypeList();

    /**
     * 添加维修项目
     * @param repairItem 要添加的维修项目
     * @return 影响的行数
     */
    int addRepairItem(RepairItem repairItem);

    /**
     * 更新维修项目
     * @param repairItem 要更新的维修项目
     * @return 影响的行数
     */
    int updateRepairItem(RepairItem repairItem);

    /**
     * 根据id删除维修项目
     * @param id 要删除的维修项目
     * @return 影响的行数
     */
    int deleteRepairItem(int id);
}
