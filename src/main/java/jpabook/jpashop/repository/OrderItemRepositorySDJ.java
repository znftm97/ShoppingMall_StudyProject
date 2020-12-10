package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepositorySDJ extends JpaRepository<OrderItem, Long> {

    @Query("select oi from OrderItem oi where oi.id = :id")
    OrderItem findOne(@Param("id") Long id);
}
