package com.ray.ipasample.Repository;

import com.ray.ipasample.domain.Member;
import com.ray.ipasample.domain.Order;
import com.ray.ipasample.dto.OrderSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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

    public List<Order> findAllByJpql(OrderSearchCriteria orderSearch) {
       String jpql = "select o from Order o join o.member m";
       boolean isFirstCondition = true;
       if (orderSearch.getOrderStatus() != null) {
           if (isFirstCondition) {
               jpql += " where ";
               isFirstCondition = false;
           } else {
               jpql += " and ";
           }
           jpql += "o.status = :status";
       }


       // 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where ";
                isFirstCondition = false;
            } else {
                jpql += " and ";
            }
            jpql += "m.name like :name";
        }

        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class).setMaxResults(100);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    // JPA Criteria(JPA 표준 동적 쿼리문 Spec) -> 실행시 JPQl로 변환 되어 실행된다. 직관적이지 않아서 유지보수가 어렵다
    public List<Order> findAllByCriteria(OrderSearchCriteria orderSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class); // Order 엔티티를 대상으로 하는 쿼리문 객체 생성
        Root<Order> order = criteriaQuery.from(Order.class);
        Join<Order, Member> member = order.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = criteriaBuilder.equal(order.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    criteriaBuilder.like(member.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        criteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery).setMaxResults(1000); //최대 1000건
        return query.getResultList();

    }

    public void remove(Order order) {
//        entityManager.remove(order);
//        entityManager.createQuery("delete from Order where id = :id").setParameter("id", order.getId());
    }

    // 검색 기능 ...

}
