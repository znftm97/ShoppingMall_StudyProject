package jpabook.jpashop.service;

import jpabook.jpashop.domain.Coupon;
import jpabook.jpashop.repository.CouponRepositorySDJ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepositorySDJ couponRepositorySDJ;

    @Transactional
    public void coupon(String name, int discountRate){
        Coupon coupon = Coupon.createCoupon(name, discountRate);
        couponRepositorySDJ.save(coupon);
    }

}
