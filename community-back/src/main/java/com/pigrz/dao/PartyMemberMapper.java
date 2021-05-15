package com.pigrz.dao;

import com.pigrz.pojo.PartyMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PartyMemberMapper {
    /**
     * 获取所有党员信息
     * @return 所有党员信息
     */
    List<PartyMember> getAllPartyMember();


    /**
     * 根据名字查询某个党员信息
     * @param name 党员name
     * @return 某个党员的信息
     */
    List<PartyMember> getPartyMemberByName(String name);

    PartyMember getPartyMemberByTelephone(String telephone);

    /**
     * 创建党员
     * @param partyMember 要创建的党员
     * @return 影响的行数
     */
    int addPartyMember(PartyMember partyMember);

    /**
     * 修改党员
     * @param partyMember 要修改的党员
     * @return 影响的行数
     */
    int updatePartyMember(PartyMember partyMember);

    /**
     * 指定id党员工作
     * @param id 要工作的党员
     * @return 影响的行数
     */
    int setPartyMemberWork(int id);

    /**
     * 指定id党员休息
     * @param id 要休息的党员
     * @return 影响的行数
     */
    int setPartyMemberNoWork(int id);

    /**
     * 删除党员
     * @param id 党员id
     * @return 影响的行数
     */
    int deletePartyMember(int id);

    List<PartyMember> getWorkPartyMember();
}
