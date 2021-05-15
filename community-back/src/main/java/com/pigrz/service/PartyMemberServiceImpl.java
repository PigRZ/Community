package com.pigrz.service;

import com.pigrz.dao.PartyMemberMapper;
import com.pigrz.pojo.PartyMember;
import com.pigrz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyMemberServiceImpl implements PartyMemberService{
    @Autowired
    PartyMemberMapper partyMemberMapper;


    @Override
    public List<PartyMember> getAllPartyMember() {
        return partyMemberMapper.getAllPartyMember();
    }

    @Override
    public List<PartyMember> getPartyMemberByName(String name) {
        if(StringUtil.nullOrEmpty(name)) {
            return new ArrayList<>();
        }
        else {
            return partyMemberMapper.getPartyMemberByName(name);
        }
    }

    @Override
    public PartyMember getPartyMemberByTelephone(String telephone) {
        if(StringUtil.nullOrEmpty(telephone)){
            return null;
        }
        else{
            return partyMemberMapper.getPartyMemberByTelephone(telephone);
        }
    }

    @Override
    public boolean addPartyMember(PartyMember partyMember) {
        if(partyMember==null) {
            return false;
        }
        else{
            partyMember.setWork("否");
            return partyMemberMapper.addPartyMember(partyMember)>0;
        }
    }

    @Override
    public boolean updatePartyMember(PartyMember partyMember) {
        if(partyMember==null) {
            return false;
        }
        else{
            return partyMemberMapper.updatePartyMember(partyMember)>0;
        }
    }

    @Override
    public boolean setPartyMemberWork(String id) {
        if(StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else {
            try {
                int i=Integer.parseInt(id);
                return partyMemberMapper.setPartyMemberWork(i)>0;
            }catch (Exception e){
                System.out.println("设置党员工作状态时id不合法:"+id);
                return false;
            }
        }
    }

    @Override
    public boolean setPartyMemberNoWork(String id) {
        if(StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else {
            try {
                int i=Integer.parseInt(id);
                return partyMemberMapper.setPartyMemberNoWork(i)>0;
            }catch (Exception e){
                System.out.println("设置党员工作状态时id不合法:"+id);
                return false;
            }
        }
    }

    @Override
    public boolean deletePartyMember(String id) {
        if(StringUtil.nullOrEmpty(id)) {
            return false;
        }
        else {
            try {
                int i=Integer.parseInt(id);
                return partyMemberMapper.deletePartyMember(i)>0;
            }catch (Exception e){
                System.out.println("删除党员时id不合法:"+id);
                return false;
            }
        }
    }

    @Override
    public List<PartyMember> getWorkPartyMember() {
        return partyMemberMapper.getWorkPartyMember();
    }
}
