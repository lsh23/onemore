package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.Member;
import com.formuscle.onemore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers() { return memberRepository.findAll();}

    public Member findOne(Long memberId) {return memberRepository.findOne(memberId);}

}
