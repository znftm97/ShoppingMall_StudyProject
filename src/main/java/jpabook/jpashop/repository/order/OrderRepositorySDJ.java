package jpabook.jpashop.repository.order;

import jpabook.jpashop.domain.Coupon;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepositorySDJ extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.status = :orderStatus")
    Page<Order> findByStatus(Pageable pageable, @Param("orderStatus") OrderStatus orderStatus);

    @Query("select o from Order o where o.id = :orderId")
    Order findByOrderId(@Param("orderId") Long orderId);
}
