package com.ray.ipasample.Repository;

import com.ray.ipasample.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

//    @Autowired
//    public OrderRepository(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOne(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> findAll() {
        return entityManager.createQuery("select o from Order o", Order.class).getResultList();
    }

    public void remove(Order order) {
//        entityManager.remove(order);
//        entityManager.createQuery("delete from Order where id = :id").setParameter("id", order.getId());
    }

    // 검색 기능 ...

}
