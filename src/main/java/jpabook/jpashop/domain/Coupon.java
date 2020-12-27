package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Coupon {

    @GeneratedValue @Id
    @Column(name = "coupon_id")
    private Long id;

    private String couponName;

    private LocalDateTime initDate;

    private int discountRate;
    

    //==생성 메서드==//
    public static Coupon createCoupon(String name, int discountRate){
        Coupon coupon = new Coupon();
        coupon.setCouponName(name);
        coupon.setInitDate(LocalDateTime.now());
        coupon.setDiscountRate(discountRate);

        return coupon;
    }
}
