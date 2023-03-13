package com.example.numble.timedeal.service;

import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.domain.Order;
import com.example.numble.timedeal.domain.OrderItem;
import com.example.numble.timedeal.domain.item.Item;
import com.example.numble.timedeal.repository.MemberRepository;
import com.example.numble.timedeal.repository.OrderRepository;
import com.example.numble.timedeal.repository.OrderSearch;
import com.example.numble.timedeal.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static com.example.numble.timedeal.domain.Order.createOrder;
import static com.example.numble.timedeal.domain.OrderItem.createOrderItem;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findByMemberId(memberId);
        Item item = itemRepository.findById(itemId).get();

        //orderItem 생성
        int orderPrice = item.getPrice();
        OrderItem orderItem = createOrderItem(item,orderPrice, count);

        //주문 생성
        Order order = createOrder(member, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getOrderId();
    }


    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 조회
        Order order = orderRepository.findByOrderId(orderId);
        //주문 취소
        order.cancel();
    }

//
//    검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }



}
