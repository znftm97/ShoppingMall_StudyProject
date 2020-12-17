package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.order.OrderRepositorySDJ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class OrderTest {

    @Autowired
    OrderRepositorySDJ orderSdj;
    @Autowired
    EntityManager em;

    @Test
    public void typeCehck() throws Exception {
        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);

        OrderStatus status = order.getStatus();
        String string = "ORDER";

        System.out.println("===================================");
        System.out.println("string = " + string.getClass().getName());
        System.out.println("status = " + status.name().getClass().getName());
        System.out.println("status = " + status.getClass().getName());
    }



    @Test
    public void contentCheck() throws Exception {
        Member member1 = createMember("userA", "서울", "1", "1111");
        em.persist(member1);

        Book book1 = createBook("11 Book", 10000, 100);
        em.persist(book1);

        OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);

        Delivery delivery = createDelivery(member1);
        Order order = Order.createOrderWithCoupon(member1, delivery, orderItem1);
        em.persist(order);

        //2
        Member member2 = createMember("userB", "인천", "2", "2222");
        em.persist(member2);

        Book book2 = createBook("22 Book", 20000, 200);
        em.persist(book2);

        OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 3);

        Delivery delivery2 = createDelivery(member2);
        Order order2 = Order.createOrderWithCoupon(member2, delivery2, orderItem2);
        em.persist(order2);

        // 3
        Member member3 = createMember("userC", "인천", "2", "2222");
        em.persist(member3);

        Book book3 = createBook("33 Book", 20000, 200);
        em.persist(book3);

        OrderItem orderItem3 = OrderItem.createOrderItem(book3, 20000, 3);

        Delivery delivery3 = createDelivery(member3);
        Order order3 = Order.createOrderWithCoupon(member3, delivery3, orderItem3);
        em.persist(order3);

        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "id"));
        Page<Order> baskets = orderSdj.findByStatus(pageRequest, OrderStatus.ORDER);

        int number = baskets.getNumber();
        System.out.println("number = " + number);

        List<Order> content = baskets.getContent();
        for (Order order1 : content) {
            System.out.println("order1 = " + order1);
        }

        long totalElements = baskets.getTotalElements();
        System.out.println("totalElements = " + totalElements);
    }


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

}
