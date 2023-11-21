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

    private String name;

    @Embedded // 내장된 타입임을 명시
    private Address address;

    private List<Orders> orders = new ArrayList<>();
}