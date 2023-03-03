package com.example.numble.timedeal.service;

import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.domain.Order;
import com.example.numble.timedeal.domain.OrderStatus;
import com.example.numble.timedeal.domain.item.Book;
import com.example.numble.timedeal.domain.item.Item;
import com.example.numble.timedeal.exception.NotEnoughStockException;
import com.example.numble.timedeal.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember("test");
        Item item = createBook("test", 10000,10);

        //when
        int orderCount =2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order order = orderRepository.findByOrderId(orderId);

        Assert.assertEquals(OrderStatus.ORDER, order.getStatus());
        Assert.assertEquals(1, order.getOrderItems().size());
        Assert.assertEquals(10000*orderCount, order.getTotalPrice());
        Assert.assertEquals(8, item.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember("test");
        Item item = createBook("test", 10000,10);
        int orderCount =2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Assert.assertEquals(8, item.getStockQuantity());
        //when
        orderService.cancelOrder(orderId);
        //then
        Order order = orderRepository.findByOrderId(orderId);

        Assert.assertEquals(OrderStatus.CANCEL, order.getStatus());
        Assert.assertEquals(10, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember("test");
        Item item = createBook("test", 10000,10);
        //when
        int orderCount = 11;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //then
        fail("재고 수량 부족 예외가 발생하지 않았습니다.");
    }

    private Book createBook(String name, int orderPrice, int stockQuantity){
        Book item = new Book();
        item.setName(name);
        item.setPrice(orderPrice);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

    private Member createMember(String name){
        Member member = new Member();
        member.setName(name);
        em.persist(member);

        return member;
    }

}