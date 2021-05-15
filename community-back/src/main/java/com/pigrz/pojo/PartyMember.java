package com.pigrz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyMember {
    private int id;
    private String name;
    private String telephone;
    private String work;
}
