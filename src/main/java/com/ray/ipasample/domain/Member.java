package com.ray.ipasample.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id") // 테이블 컬럼명을 기술
    private Long id;

    @Column(unique=true)
    private String name;

    @Embedded // 내장된 타입임을 명시
    private Address address;

    // Member 의 입장에서 orders(멤버변수)는 일대다 관계
    // Order 객체에 있는 member 가 연관관계의 주인이고, 현재 orders 필드(멤버변수)는 거울임
    // mappedBy 속성은 연관관꼐에서 주인이 아닌 필드에 기술
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}