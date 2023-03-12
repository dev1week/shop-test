package com.example.numble.timedeal.Dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class LoginRequest {
    @NotNull
    private Long id;

    @NotEmpty
    private String password;
}
