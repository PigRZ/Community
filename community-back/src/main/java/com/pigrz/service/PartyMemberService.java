package com.pigrz.service;

import com.pigrz.pojo.PartyMember;

import java.util.List;

public interface PartyMemberService {

    //党员管理
    List<PartyMember> getAllPartyMember();
    List<PartyMember> getPartyMemberByName(String name);
    PartyMember getPartyMemberByTelephone(String telephone);
    boolean addPartyMember(PartyMember partyMember);
    boolean updatePartyMember(PartyMember partyMember);
    boolean setPartyMemberWork(String id);
    boolean setPartyMemberNoWork(String id);
    boolean deletePartyMember(String id);

    List<PartyMember> getWorkPartyMember();
}
