package com.formuscle.onemore.api;

import com.formuscle.onemore.domain.Member;
import com.formuscle.onemore.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/member/new")
    public JoinMemberResponse member_join(@RequestBody @Valid JoinMemberRequest request) {

        Member member = new Member();
        String name = request.name;
        String email = request.email;
        String psw = request.psw;

        member.setUsername(name);
        member.setPassword(passwordEncoder.encode(psw));
        member.setUserMail(email);
        memberService.join(member);

        return new JoinMemberResponse(name,email);
    }

    @Data
    @AllArgsConstructor
    static class JoinMemberResponse {
        private String memberName;
        private String memberMail;
    }

    @Data
    @AllArgsConstructor
    static class JoinMemberRequest {

        private String email;
        private String psw;
        private String name;
    }
}
