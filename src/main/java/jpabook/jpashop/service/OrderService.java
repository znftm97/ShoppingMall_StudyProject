package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepositorySDJ orderItemRepositorySDJ;
    private final CouponRepositorySDJ couponRepositorySdj;

    /**
     * 주문
     */
    @Transactional
    public Long orderWithCoupon(Long memberId, Long itemId, int count, Long couponId){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        Coupon coupon = couponRepositorySdj.findByCoupon(couponId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //금액 할인
        int itemPrice = item.getPrice(); // 100000
        int discountRate = coupon.getDiscountRate(); // 10프로
        int itemDiscountPrice = itemPrice - (itemPrice/discountRate); // 90000원 (10000원 할인)

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, itemDiscountPrice, count);

        //주문 생성
        Order order = Order.createOrderWithCoupon(member, delivery, coupon, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //장바구니에 담기
    @Transactional
    public Long orderBasketWithCoupon(Long memberId, Long itemId, int count, Long couponId){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        Coupon coupon = couponRepositorySdj.findByCoupon(couponId);


        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //금액 할인
        int itemPrice = item.getPrice();
        int discountRate = coupon.getDiscountRate();
        int itemDiscountPrice = itemPrice - (itemPrice % itemPrice);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItemBasket(item, itemDiscountPrice, count);

        //주문 생성
        Order order = Order.createBasketWithCoupon(member, delivery, coupon, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public Long orderBasket(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItemBasket(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createBasket(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderID){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderID);
        //주문 취소
        order.cancel();

        // 각 아이템별로 리스트 뿌렸을때 취소 로직
        /*OrderItem orderItem = orderItemRepositorySDJ.findOne(orderID);
        orderItem.getOrder().cancel();*/
    }

    //OrderStatus ORDER로 변경
    @Transactional
    public void statusChangeOrder(Long id){
        Order order = orderRepository.findOne(id);
        order.setStatus(OrderStatus.ORDER);
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }

    public List<Coupon> findCoupons() {
        return couponRepositorySdj.findAll();
    }
}
