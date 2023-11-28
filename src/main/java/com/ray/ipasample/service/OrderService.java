package com.ray.ipasample.service;

import com.ray.ipasample.Exception.NotEnoughStockException;
import com.ray.ipasample.Repository.ItemRepository;
import com.ray.ipasample.Repository.MemberRepository;
import com.ray.ipasample.Repository.OrderRepository;
import com.ray.ipasample.domain.*;
import com.ray.ipasample.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    
    // 주문
    public void order(Long memberId, Long itemId, int count) throws NotEnoughStockException {

        // memberId로 주문자 얻어오기
        Member orderOwner = memberRepository.find(memberId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(orderOwner.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 상품 정보 생성
        Item buyItem = itemRepository.findOne(itemId);
        OrderItem orderItem = OrderItem.createOrderItem(buyItem, buyItem.getPrice(), count);

        Order order = Order.createOrder(orderOwner, delivery, orderItem);
        orderRepository.save(order);

        // Order 엔티티의 Cascade 옵션 덕분에 Order 엔티티만 저장해도 배송정보, OrderItem 엔티티가 함께 저장된다.
    }
    
    // 주문 취소
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId); // 주문한 Order 엔티티 조회
        order.orderCancel(); // 주문 취소

        // 트렌젝션 스크립트 패턴(Transaction 이 일어나는 곳에서 mapper(SQL 문)를 호출하는 기술 패턴)
        // Mybatis 등의 SQL Mapper 기술들은 어떤 엔티티가 가지고 있는 멤버의 값이 변경될때 일일이 update 를 각각 해줘야하는 경향이 있다.

        // 도메인 모델 패턴(JPA 의 경우) : 엔티티가 가지고 있는 domain 내부에서 비즈니스 로직을 풀어냄 -> 서비스단의 로직이 트랜잭션스크립트패턴보다 간결해진다
        // -> JPA 는 엔티티가 가지고 있는 멤버의 값이 변경됨을 감지하고 직접 영속성 컨텍스트에 값을 반영하게 된다.
    }
    
    // 주문 조회(검색)
}
