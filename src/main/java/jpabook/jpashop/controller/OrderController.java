package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Coupon;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.CouponRepositorySDJ;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.OrderItemRepository;
import jpabook.jpashop.repository.OrderItemRepositorySDJ;
import jpabook.jpashop.repository.order.OrderRepositorySDJ;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderRepositorySDJ orderSdj;
    private final OrderItemRepositorySDJ orderItemSdj;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final CouponRepositorySDJ couponSdj;

    //주문 화면
    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        List<Coupon> coupons = orderService.findCoupons();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        model.addAttribute("coupons", coupons);

        return "/order/orderForm";
    }

    //주문 목록 출력
    @GetMapping("/orders")
    public String orderList(Model model, @PageableDefault(size = 4, sort = "id") Pageable pageable) {
        Page<Order> orders = orderSdj.findByStatus(pageable, OrderStatus.ORDER);

        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    //주문
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count,
                        @RequestParam("couponId") Long couponId
                        ){

        orderService.orderWithCoupon(memberId, itemId, count, couponId);
        return "redirect:/orders";
    }

    //주문 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";

    }
    /*******장바구니 *******/

    // 장바구니에서 바로 주문하기
    @PostMapping("/basketsOrder/{orderId}/basket")
    public String basketOrder(@PathVariable("orderId") Long orderId){
        Order order = orderSdj.findByOrderId(orderId);

        orderService.statusChangeOrder(orderId);

        for (int i = 0; i < order.getOrderItems().size(); i++) {
            Item item = order.getOrderItems().get(i).getItem();
            itemService.removeStock(item, order.getOrderItems().get(i).getCount());
        }
        return "redirect:/orders";
    }

    //장바구니 목록 출력
    @GetMapping("/baskets")
    public String baksetList(Model model, @PageableDefault(size = 2, sort = "id") Pageable pageable){
        Page<Order> baskets = orderSdj.findByStatus(pageable, OrderStatus.BASKET);

        model.addAttribute("baskets", baskets);

        return "order/basketList";
    }

    //장바구니에 담기 버튼
    @PostMapping("/baskets")
    public String basket(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count,
                         @RequestParam("couponId") Long couponId
                         ){

        // 장바구니에 담는 서비스 로직
        orderService.orderBasketWithCoupon(memberId, itemId, count, couponId);

        return "redirect:/baskets";
    }

    /*//주문 목록
    @GetMapping("/orders")
    public String orderList(@PageableDefault(size = 2, sort = "id") Pageable pageable, @ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        //List<Order> orders = orderService.findOrders(orderSearch); // 바로 orderRepository.findAllByString(orderSearch) 호출해도 됨

        *//*모든 주문 내역 출력, 페이징, 검색과 취소기능 비활성화
        Page<OrderItem> orders = orderItemSdj.findAll(pageable);*//*


        Page<Order> orders = orderSdj.findAll(pageable);

        model.addAttribute("orders", orders);

        *//*모든 주문 내역 출력, 그러나 검색 못함..
        List<OrderItem> orderItems = orderItemRepository.findAll();
        model.addAttribute("orders", orderItems);*//*

        return "order/orderList";
    }*/




}
