package com.ray.ipasample;

import com.ray.ipasample.Repository.MemberRepository;
import com.ray.ipasample.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // Spring 환경에서 현재 객체를 수행
@SpringBootTest // Spring Boot 환경에서 테스트를 수행
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional // test case에서 test후에 rollback default
    @Rollback(value = false) // Rollback 하지 않고 commit
    public void testMember() {
        // Given (최초의 테스트시에 주어지는 환경, 조건 등을 기술 : 테스트 준비 과정)
        Member member = new Member();
        member.setName("ray");


        // When (~을 할때... 테스트 시작)
        Long registerMemberId = memberRepository.save(member);
        Member findMember = memberRepository.find(registerMemberId);

        // Then (테스트가 통과하는지 실패하는지 검증)
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId()); // id 동일
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());

        // JPA에서는 같은 transaction 안에서 컨텍스트가 같기 때문에, DB에서 가져온 findMember와 메모리에 있는 member는 동일한 주소를 가지고 있다
        Assertions.assertThat(findMember).isEqualTo(member);
        
        if(member == findMember) {
            System.out.println("같다");
        }

    }
}
