package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.Member;
import com.formuscle.onemore.repository.MemberRepository;
import com.formuscle.onemore.repository.MuscleTargetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 회원_추가() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");

        // When
        Long memberId = memberService.join(member);

        // Then
        assertEquals(member,memberRepository.findOne(memberId));
    }

    @Test
    public void 회원_이메일로_조회() throws Exception {
        // Given

        String username = "Sehyeong";
        String userMail = "shseoul14@gmail.com";

        Member member = new Member();

        member.setUsername(username);
        member.setUserMail(userMail);

        // When
        Long memberId = memberService.join(member);

        // Then
        assertEquals(member,memberRepository.findOneByEmail(userMail));
    }
}