package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;
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
 *      SPRING2
 *      */


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

            Album album1 = createAlbum("a", 100, 100);
            em.persist(album1);
            Album album2 = createAlbum("b", 100, 100);
            em.persist(album2);
            Album album3 = createAlbum("c", 100, 100);
            em.persist(album3);
            Album album4 = createAlbum("d", 100, 100);
            em.persist(album4);
            Album album5 = createAlbum("e", 100, 100);
            em.persist(album5);
            Album album6 = createAlbum("f", 100, 100);
            em.persist(album6);
            Movie movie1 = createMovie("g", 100, 100);
            em.persist(movie1);
            Movie movie2 = createMovie("h", 100, 100);
            em.persist(movie2);
            Movie movie3 = createMovie("i", 100, 100);
            em.persist(movie3);
            Movie movie4 = createMovie("j", 100, 100);
            em.persist(movie4);
            Movie movie5 = createMovie("k", 100, 100);
            em.persist(movie5);
            Movie movie6 = createMovie("m", 100, 100);
            em.persist(movie6);

            OrderItem orderItem1 = OrderItem.createOrderItem(album5, 100, 2);
            Delivery delivery1 = createDelivery(member1);
            Order order1 = Order.createOrder(member1, delivery1, orderItem1);
            em.persist(order1);

            OrderItem orderItem2 = OrderItem.createOrderItem(album6, 100, 20);
            Delivery delivery2 = createDelivery(member1);
            Order order2 = Order.createOrder(member1, delivery2, orderItem2);
            em.persist(order2);

            OrderItem orderItem3 = OrderItem.createOrderItem(movie1, 100, 30);
            Delivery delivery3 = createDelivery(member1);
            Order order3 = Order.createOrder(member1, delivery3, orderItem3);
            em.persist(order3);

            OrderItem orderItem4 = OrderItem.createOrderItem(album2, 100, 40);
            Delivery delivery4 = createDelivery(member1);
            Order order4 = Order.createOrder(member1, delivery4, orderItem4);
            em.persist(order4);

            OrderItem orderItem5 = OrderItem.createOrderItemBasket(album2, 100, 40);
            Delivery delivery5 = createDelivery(member1);
            Order order5 = Order.createBasket(member1, delivery5, orderItem5);
            em.persist(order5);

            OrderItem orderItem6 = OrderItem.createOrderItemBasket(album2, 100, 40);
            Delivery delivery6 = createDelivery(member1);
            Order order6 = Order.createBasket(member1, delivery6, orderItem6);
            em.persist(order6);

            OrderItem orderItem7 = OrderItem.createOrderItemBasket(album2, 100, 40);
            Delivery delivery7 = createDelivery(member1);
            Order order7 = Order.createBasket(member1, delivery7, orderItem7);
            em.persist(order7);


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

        private Album createAlbum(String name, int price, int stockQuantity) {
            Album album = new Album();
            album.setName(name);
            album.setPrice(price);
            album.setStockQuantity(stockQuantity);
            album.setItemType("Album");
            return album;
        }

        private Movie createMovie(String name, int price, int stockQuantity) {
            Movie movie = new Movie();
            movie.setName(name);
            movie.setPrice(price);
            movie.setStockQuantity(stockQuantity);
            movie.setItemType("Movie");
            return movie;
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
