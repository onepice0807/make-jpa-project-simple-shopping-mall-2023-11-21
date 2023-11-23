package com.ray.ipasample.domain;

import javax.persistence.*;

public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id") // 테이블 컬럼명을 기술
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private  Address address;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL 은 실무에서 사용하지 않는다. 중간에 업데이트가 되면 문제발생..
    private DeliveryStatus status; // READY(배송준비중) CANCEL(배송취소)
}
