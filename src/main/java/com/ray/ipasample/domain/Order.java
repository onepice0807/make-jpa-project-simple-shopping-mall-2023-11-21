package com.ray.ipasample.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // SQL 키워드인 order by 와의 혼동을 피하기 위해 테이블명을 orders라고 지정
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id") // 컬럼명 기술
    private Long id;

    // FetchType.EAGER 가 default (즉시 인출 하는 전력 -> order객체를 참조할때마다 조인 쿼리문이 실행됨. 성능 저하
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    //  조인할때 조인 조건에 사용할 컬럼 기술(FK) // 연관관계의 주인필드에서 @JoinColumn 기술 (fk가 있는 쪽이 주인)
    private Member member; // 현재 Order 를 주문한 주문자(행위의 주인)

    // cascade 속성 값을 주지 않으면 Order 클래스에 있는 모든 엔티티에 대해 persist(insert), marge(update), remove 를(delete) 별도로 해야 한다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // FK => 연관관계의 주인
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    // EnumType.ORDINAL 은 내부에서 ORDER, CANCEL 을 구분하는 상태값을 정수로 구분한다.
    // 상태값이 추가되거난 삭제되면 위험해지므로 EnumType.STRING 사용을 추천한다
    @Enumerated(EnumType.STRING)
    private  OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // 연관 관게의 편의 메서드(현재 엔티티(Order)의 값이 변경될 때 연관되어 있는 엔티티의 값도 바뀔 수 있도록 하는 메서드) 추가
    // 연관 관계 편의 메서드는 실제적인 행위를 컨트롤 하는 역할이 되는 엔티티에 만드는 것이 좋다.
    public void setMember(Member orderMember) {
        this.member = orderMember;
        this.member.getOrders().add(this); // 현재 주문자의 주문 목록에도 현재 주문을 넣어주어야 한다.
    }

    public void setDeliveryI(Delivery orderedDelivery) {
        this.delivery = orderedDelivery;
        this.delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem); // 주문 상품 목록에 현재 주문상품을 넣어둠
        orderItem.setOrder(this); // 주문 상품에도 현재 주문을 넣어둠
    }

    // 생성 메서드 (주문을 생성하는)
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        // orderItems(주문상품)은 여러개가 될 수 있기 때문에 가변인자 매개변수 처리

        Order order = new Order();
        order.setMember(member); // 주문자
        order.setDelivery(delivery); // 배송지

        for (OrderItem orderItem : orderItems) { // 주문한 상품
            order.addOrderItem(orderItem);
        }

        order.setOrderDate(LocalDateTime.now()); // 주문시간

        order.setStatus(OrderStatus.ORDER); //  주문상태로 변경

        return order;
    }

    // 주문 취소 메서드
    public void orderCancel() {
        if (delivery.getStatus() == DeliveryStatus.COMPLETE) { // 배송 완료된 상품
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다!!!");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 각각의 주문된 상품에 대해 취소 시키는 메서드
        }
    }
    
    // 주문 조회
    // 전체 주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalOrderItemPrice();
        }
        return totalPrice;
    }
}
