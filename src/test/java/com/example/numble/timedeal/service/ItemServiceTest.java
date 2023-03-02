package com.example.numble.timedeal.service;

import com.example.numble.timedeal.domain.item.Book;
import com.example.numble.timedeal.exception.NotEnoughStockException;
import com.example.numble.timedeal.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.example.numble.timedeal.domain.item.Item;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired
    ItemRepository itemRepository;


    @Autowired
    ItemService itemService;

    @Test
    public void 정상저장및조회테스트() throws Exception{
        //given
        Book item = new Book();
        item.setName("test");

        //when
        itemService.saveItem(item);

        //then
        Item savedItem = itemService.findItem(item.getId());
        Assert.assertEquals(savedItem.getId(), item.getId());
    }

    @Test
    public void 아이템수량감소로직테스트() throws Exception{
        //given
        Book test = new Book();
        test.setName("test");
        test.setStockQuantity(1);

        //when
        test.discriminateStock(1);
        //then

        Assert.assertEquals(0, test.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 아이템수량이0으로떨어지게될경우에러테스트() throws Exception{
        //given
        Book test = new Book();
        test.setName("test");
        test.setStockQuantity(3);
        //when
        test.discriminateStock(4);
        //then
        //예외를 반환해야 정상
        //예외를 반환하지 않을 경우

        fail("예외가 발생해야한다.");

    }





}