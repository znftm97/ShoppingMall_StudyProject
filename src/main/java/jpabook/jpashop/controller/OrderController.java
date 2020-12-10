package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderItemRepository;
import jpabook.jpashop.repository.OrderItemRepositorySDJ;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.OrderRepositorySDJ;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderRepositorySDJ orderSdj;
    private final OrderItemRepositorySDJ orderItemSdj;
    /*private final OrderItemRepository orderItemRepository;*/

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "/order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@PageableDefault(size = 2, sort = "id") Pageable pageable, @ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        //List<Order> orders = orderService.findOrders(orderSearch); // 바로 orderRepository.findAllByString(orderSearch) 호출해도 됨

        /*모든 주문 내역 출력, 페이징, 검색과 취소기능 비활성화
        Page<OrderItem> orders = orderItemSdj.findAll(pageable);*/

        Page<Order> orders = orderSdj.findAll(pageable);
        model.addAttribute("orders", orders);

        /*모든 주문 내역 출력, 그러나 검색 못함..
        List<OrderItem> orderItems = orderItemRepository.findAll();
        model.addAttribute("orders", orderItems);*/

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }




}
