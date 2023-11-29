package com.ray.ipasample.dto;

import com.ray.ipasample.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchCriteria {

    private String memberName;
    private OrderStatus orderStatus; // 주문상태
}
