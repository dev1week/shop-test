package com.example.numble.timedeal.Dto;

import com.example.numble.timedeal.domain.RoleType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateMemberRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    private RoleType roleType;
}
