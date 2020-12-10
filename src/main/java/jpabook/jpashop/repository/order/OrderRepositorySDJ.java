package jpabook.jpashop.repository.order;

import jpabook.jpashop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositorySDJ extends JpaRepository<Order, Long> {
}
