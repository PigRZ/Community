package com.pigrz.dao;

import com.pigrz.pojo.Order;
import com.pigrz.pojo.Repair;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface RepairMapper {
    /**
     * 获取所有社区居民的报修记录
     * @return 所有社区居民的报修记录
     */
    List<Repair> getAllRepair();

    List<Repair> getNoDoneRepair();

    /**
     * 根据日期获取报修记录
     * @param date 报修记录创建日期
     * @return  该日期创建的所有报修记录
     */
    List<Repair> getRepairByDate(Date date);


    /**
     * 查询某个用户的所有报修记录
     * @param userid 用户id
     * @return 某个用户的所有报修记录
     */
    List<Repair> getRepairByUserid(int userid);

    /**
     * 查询某个用户的所有报修记录
     * @param username 用户名称
     * @return 某个用户的所有报修记录
     */
    List<Repair> getRepairByUsername(String username);


    /**
     * 查询某个维修项目的所有报修记录
     * @param repairitemname 维修项目名称
     * @return 某个维修项目的所有报修记录
     */
    List<Repair> getRepairByRepairitemname(String repairitemname);

    /**
     * 根据用户id和维修项目id查报修记录
     * @param userid 用户id
     * @param repairitemid 维修项目id
     * @return 报修记录
     */
    Repair getRepairByUseridAndRepairitemid(int userid,int repairitemid);

    /**
     * 创建报修记录
     * @param repair 要创建的报修记录
     * @return 影响的行数
     */
    int addRepair(Repair repair);

    /**
     * 修改报修记录
     * @param repair 要修改的报修记录
     * @return 影响的行数
     */
    int updateRepair(Repair repair);

    int completeRepair(int id);

    /**
     * 删除报修记录
     * @param id 报修记录id
     * @return 影响的行数
     */
    int deleteRepair(int id);
}
