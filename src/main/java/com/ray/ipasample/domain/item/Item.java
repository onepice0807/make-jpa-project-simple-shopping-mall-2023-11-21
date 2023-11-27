package com.ray.ipasample.domain.item;

import com.ray.ipasample.Exception.NotEnoughStockException;
import com.ray.ipasample.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 관계의 클래스임을 명시. (상속전략 SINGLE_TABLE : 부모 자식 클래스를 하나의 테이블로 매핑
@DiscriminatorColumn(name = "dtype") // 테이블에서 자식 객체를 구분 하게 하는 컬럼
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    protected Item() {}

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    @ManyToMany
    @JoinTable(name = "category_item", // DB의 다대다 관계를 풀어줄 중간 테이블과의 조인 정보 기입
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Category> categories = new ArrayList<>();

    // 비즈니스 로직 추가(data 를 가지고 있는 쪽에  비즈니스 로직이 있는 것이 좋은 소프트웨어의 조건인 응집력을 만족시킨다)

    // 재고수량 증가
    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    // 재고수량 감소 (판매, 재고관리)
    public void removeStock(int stockQuantity) throws  NotEnoughStockException {
        if (this.stockQuantity - stockQuantity < 0) {
            throw new NotEnoughStockException("Stock quantity cannot be less than 0");
        }
        this.stockQuantity -= stockQuantity;
    }
}
