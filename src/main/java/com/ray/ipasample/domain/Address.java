package com.ray.ipasample.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable  // 다른 엔티티의 값 타입으로 내장 될 수 있음을 의미
@Getter
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 기본 생성자는 보통 실무에서는 public 하게 만들지 않는 것을 원칙으로 함.(아무렇게나 객체를 만들 수 없도록)
    // JPA 처럼 Proxy 기법을 사용하는 api 는 기본생성자를 만들어 줘야 객체에 접근이 가능하다. protect 접근자 까지는 접근 가능
    protected Address() {}
}
