package com.example.numble.timedeal.Dto;

import com.example.numble.timedeal.domain.Order;
import com.example.numble.timedeal.domain.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String name;
    private String password;
    private RoleType roleType;
    private Date createdDate;
    private List<Order> orders = new ArrayList<>();

    public MemberDto of(Long memberId, String name, RoleType roleType) {
        return new MemberDto(memberId, name,null, roleType, null, null);
    }


}
