package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final EntityManager em;

    public List<OrderItem> findAll(){
        return em.createQuery("select i from OrderItem i", OrderItem.class)
                .getResultList();
    }

}
