package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepositorySDJ extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c")
    Page<Coupon> findAll(Pageable pageable);

    @Query("select c from Coupon c where c.id = :couponId")
    Coupon findByCoupon(@Param("couponId") Long couponId);
}
