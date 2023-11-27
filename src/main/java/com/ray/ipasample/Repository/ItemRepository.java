package com.ray.ipasample.Repository;

import com.ray.ipasample.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Item item) {
        // 상품을 최초 등록하는 것인지 수정하는 것인지
        if (item.getId() == null) {
            // 상품등록
            entityManager.persist(item);
        } else {
            // 상품 수정
            entityManager.merge(item); // update 준 영속 상태의 엔티티를 영속 상태의 엔티티로 변경
        }
    }
    
    // 하나의 상품 조회
    public Item findOne(Long id) {
        return entityManager.find(Item.class, id); // select * from Item where id = id
    }

    // 전체의 상품 조회
    public List<Item> findAll() {
        return entityManager.createQuery("select i from Item i", Item.class).getResultList();
    }
}
