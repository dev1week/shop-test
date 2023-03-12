package com.example.numble.timedeal;

import com.example.numble.timedeal.domain.Member;
import com.example.numble.timedeal.domain.Order;
import com.example.numble.timedeal.domain.OrderItem;
import com.example.numble.timedeal.domain.RoleType;
import com.example.numble.timedeal.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userA","1", RoleType.USER);
            em.persist(member);
            Book book1 = createBook("Book1", 10000, 100);
            em.persist(book1);
            Book book2 = createBook("Book2", 20000, 100);
            em.persist(book2);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            Order order = Order.createOrder(member, orderItem1, orderItem2);
            em.persist(order);
        }
        public void dbInit2() {
            Member member = createMember("userB", "2", RoleType.ADMIN);
            em.persist(member);
            Book book1 = createBook("Book3", 20000, 200);
            em.persist(book1);
            Book book2 = createBook("Book4", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            Order order = Order.createOrder(member,  orderItem1, orderItem2);
            em.persist(order);
        }
        private Member createMember(String name, String password, RoleType role) {
            Member member = new Member();
            member.setName(name);
            member.setPassword(password);
            member.setRoleType(role);
            return member;
        }
        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

    }
}