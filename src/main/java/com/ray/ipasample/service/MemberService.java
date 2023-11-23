package com.ray.ipasample.service;

import com.ray.ipasample.Repository.MemberRepository;
import com.ray.ipasample.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // 회원 등록
    @Transactional(readOnly = false)
    public Long join(Member member) throws Exception {
        // 회원 이름이 중복되었는지 검사
        validateDuplicateMemberName(member);

        memberRepository.save(member);

        return member.getId();
    }


    private void validateDuplicateMemberName(Member member) {
        // 현재 로직이 있더라도 멀티 스레드 환경에서는 얼마든지 중복 검사가 제대로 진행되지 않을 수 있다
        // 그러므로, 최후의 보루로 테이블에 unique 제약조건을 사용해야 한다
        // 테이블 제약조건을 아끼지 말자
        if (memberRepository.findByName(member.getName()).size() > 0) {
            throw new IllegalArgumentException("이름이 중복됩니다...");
        }
    }

    //    @Transactional(readOnly = true)  // readOnly = true 일때는 기본적으로 List<Member> 객체가 변경이 되지 않았다면 캐싱 되어 있는 값을 사용(성능향상)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
