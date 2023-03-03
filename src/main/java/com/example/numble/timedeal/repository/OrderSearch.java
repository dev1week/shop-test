package com.example.numble.timedeal.repository;

import com.example.numble.timedeal.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
