package com.example.numble.timedeal.repository;


import com.example.numble.timedeal.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
