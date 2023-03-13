package com.example.numble.timedeal.Dto.ItemDto;

import com.example.numble.timedeal.domain.item.Item;
import lombok.Data;

@Data
public class ItemDto{
    private String name;

    private int price;
    private int stockQuantity;


    protected ItemDto(){}

    private ItemDto(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static ItemDto of(String name, int price, int stockQuantity){
        return new ItemDto(name, price, stockQuantity);
    }

    public static  ItemDto from(Item entity){
        return new ItemDto(
                entity.getName(),
                entity.getPrice(),
                entity.getStockQuantity()
        );
    }

    public Item toEntity(){
        return Item.of(
            name, price, stockQuantity
        );
    }

}
