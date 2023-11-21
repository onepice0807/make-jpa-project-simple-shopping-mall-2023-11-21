package com.ray.ipasample.Repository;

import com.ray.ipasample.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // EntityManager 객체 주입
    private EntityManager em;

    // 회원등록
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    // 회원검색
    public Member find(Long memberId) {
        return  em.find(Member.class, memberId);
    }

}
