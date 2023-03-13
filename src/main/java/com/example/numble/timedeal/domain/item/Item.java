package com.example.numble.timedeal.domain.item;

import com.example.numble.timedeal.domain.Category;
import com.example.numble.timedeal.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
@ToString
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
//    @ManyToMany(mappedBy = "items")
//    private List<Category> categories = new ArrayList<Category>();

    //엔티티 먼으로 비즈니스 로직을 해결할 수 있다면 엔티티 안에 넣는 것이 좋다.

    //재고 수량 증가 buisness logic
    public void addStock(int quantity){this.stockQuantity += quantity;}

    //재고 수량 감소 buisness logic
    public void discriminateStock(int quantity){
        int rest = this.stockQuantity - quantity;
        validateRest(rest);
        this.stockQuantity = rest;
    }

    //재고 수량아 0 밑으로 내려갈 경우 에러 반환
    private void validateRest(int rest){
        if(rest<0){
            throw new NotEnoughStockException("재고 수량이 부족합니다.");
        }
    }

    protected Item() {
    }

    private Item(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static Item of(Long id, String name, int price, int stockQuantity){
        return new Item(id, name, price, stockQuantity);
    }

    public static Item of(String name, int price, int stockQuantity){
        return Item.of(null, name, price, stockQuantity);
    }
}

