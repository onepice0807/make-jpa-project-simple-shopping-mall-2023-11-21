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

    // FK => 연관관계의 주이
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    // EnumType.ORDINAL 은 내부에서 ORDER, CANCEL 을 구분하는 상태값을 정수로 구분한다.
    // 상태값이 추가되거난 삭제되면 위험해지므로 EnumType.STRING 사용을 추천한다
    @Enumerated(EnumType.STRING)
    private  OrderStatus status; // 주문 상태 [ORDER, CANCEL]
}
