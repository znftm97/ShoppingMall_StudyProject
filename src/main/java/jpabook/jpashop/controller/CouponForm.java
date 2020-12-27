package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CouponForm {

    private String couponName;

    @NotNull(message = "할인율은 필수 ")
    @Max(value = 100)
    private int discountRate;
}
