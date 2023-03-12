package com.example.numble.timedeal.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateMemberResponse {
    private Long id;
    public CreateMemberResponse(Long id){
        this.id = id;
    }

}
