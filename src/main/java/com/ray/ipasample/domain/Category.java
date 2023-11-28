package com.ray.ipasample.domain;

import com.ray.ipasample.domain.item.Item;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long Id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", // DB의 다대다 관계를 풀어줄 중간 테이블과의 조인 정보 기입
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
    
    // 연관관계 편의 메서드
    public void addChildCategory(Category child) {
        this.child.add(child); // 부모 노드에게 자식 노드를 추가
        child.setParent(this); // 자식 노드에게 부모 노드를 추가
    }
}
