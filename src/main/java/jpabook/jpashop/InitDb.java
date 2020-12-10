package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 2개의 주문
 * * userA
 *      JPA1 BOOK
 *      JPA2 BOOK
 * * userB
 *      SPRING1 BOOK
 *      SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        } // dbInit1

        public void dbInit2(){
            Member member = createMember("userB", "인천", "2", "2222");
            em.persist(member);

            Book book1 = createBook("SPRING1 Book", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 Book", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        } // dbInit2

        public void dbInit3(){
            Member member1 = createMember("userC", "인천", "3", "3333");
            em.persist(member1);

            Member member2 = createMember("userD", "인천", "4", "4444");
            em.persist(member2);

            Member member3 = createMember("userE", "인천", "5", "5555");
            em.persist(member3);

            Member member4 = createMember("userF", "인천", "6", "6666");
            em.persist(member4);

            Member member5 = createMember("userG", "인천", "7", "7777");
            em.persist(member5);

            Member member6 = createMember("userH", "인천", "8", "8888");
            em.persist(member6);

            Member member7 = createMember("userI", "인천", "9", "9999");
            em.persist(member7);

            Member member8 = createMember("userJ", "인천", "1-", "10");
            em.persist(member8);

            Member member9 = createMember("userK", "인천", "11", "11");
            em.persist(member9);
        } // dbInit3

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            book.setItemType("Book");
            return book;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            em.persist(member);
            return member;
        }
    } // InitService

}
