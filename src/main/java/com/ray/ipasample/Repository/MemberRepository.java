package com.ray.ipasample.Repository;

import com.ray.ipasample.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext // EntityManager 객체 주입
    private EntityManager em;

    // 회원등록
    public Long save(Member member) {
        System.out.println(em.toString());
        em.persist(member);  // 영속성 컨텍스트에 저장
        return member.getId();
    }

    // 회원 검색
    public Member find(Long memberId) {
        return em.find(Member.class, memberId);
    }

    // 이름으로 회원 검색
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)  // :name 파라메터에 name 세팅
                .getResultList();
    }

    // 전체 회원 검색
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
//                .setFirstResult(0)
//                .setMaxResults(20)
                .getResultList();   // 0~ 20개까지의 List<Member> 반환
    }

}
