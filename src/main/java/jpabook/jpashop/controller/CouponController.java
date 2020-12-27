package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Coupon;
import jpabook.jpashop.repository.CouponRepositorySDJ;
import jpabook.jpashop.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponRepositorySDJ couponRepositorySDJ;
    private final CouponService couponService;

    @GetMapping("/coupons")
    public String couponList(@PageableDefault(size = 2, sort = "id") Pageable pageable, Model model){
        Page<Coupon> coupons = couponRepositorySDJ.findAll(pageable);
        model.addAttribute("coupons", coupons);

        return "coupon/couponList";
    }

    @GetMapping("/coupon/init")
    public String couponInitForm(Model model){
        model.addAttribute("couponForm" , new CouponForm());
        return "coupon/couponForm";
    }

    @PostMapping("/coupon/new")
    public String couponInit(@Valid CouponForm form, BindingResult result){

        if(result.hasErrors()){
            return "coupon/couponForm";
        }

        couponService.coupon(form.getCouponName(), form.getDiscountRate());
        return "redirect:/coupons";
    }
}
