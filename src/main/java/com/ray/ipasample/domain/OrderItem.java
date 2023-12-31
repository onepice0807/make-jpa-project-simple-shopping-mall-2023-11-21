package com.ray.ipasample.domain;

import com.ray.ipasample.Exception.NotEnoughStockException;
import com.ray.ipasample.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order; 

    private int orderPrice; // 주문 가격
    
    private int count; // 주문 수량

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) throws NotEnoughStockException {
        OrderItem orderItem = new OrderItem();

        // orderItem(장바구니)
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 재고량에서도 차감
        item.removeStock(count);

        return orderItem;
    }


    // ---------------------------- 비즈니스 로직에 의해 만들어진 메서드들 ----------------------------

    public void cancel() {
        this.getItem().addStock(count); // 재고 수량 원복
    }

    public int getTotalOrderItemPrice() {
        // 각각의 주문 상품에 대한 중간 가격 = 상품가격(orderPrice * count)
        return this.getOrderPrice() * getCount();
    }
}
