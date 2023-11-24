package com.ray.ipasample.domain.item;

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

    @ManyToMany
    @JoinTable(name = "category_item", // DB의 다대다 관계를 풀어줄 중간 테이블과의 조인 정보 기입
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Category> categories = new ArrayList<>();
}
