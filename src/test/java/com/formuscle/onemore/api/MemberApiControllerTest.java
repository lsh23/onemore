package com.formuscle.onemore.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formuscle.onemore.domain.Member;
import com.formuscle.onemore.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String RAW_PASSWORD = "1234";
    private final String WRONG_PASSWORD = "12345";

    @Test
    public void 회원가입() throws Exception {

        Member member = setUpMember();
        String email = member.getUserMail();
        String psw = member.getPassword();
        String name = member.getUsername();
        MemberApiController.JoinMemberRequest joinMemberRequest = new MemberApiController.JoinMemberRequest(email, psw, name);

        this.mockMvc.perform(post("/api/member/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(joinMemberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberName").value(name))
                .andExpect(jsonPath("$.memberMail").value(email));
    }

    @Test
    @Transactional
    public void 로그인_성공() throws Exception {

        Member member = setUpMember();
        String email = member.getUserMail();
        memberService.join(member);

        MemberApiController.LoginMemberRequest loginMemberRequest = new MemberApiController.LoginMemberRequest(email, RAW_PASSWORD);
        this.mockMvc.perform(post("/api/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginMemberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.login").value(Boolean.TRUE));
    }

    @Test
    @Transactional
    public void 로그인_실패() throws Exception {

        Member member = setUpMember();
        String email = member.getUserMail();
        memberService.join(member);

        MemberApiController.LoginMemberRequest loginMemberRequest = new MemberApiController.LoginMemberRequest(email,WRONG_PASSWORD);
        this.mockMvc.perform(post("/api/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginMemberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.login").value(Boolean.FALSE));
    }

    @Test
    @Transactional
    public void 로그아웃() throws Exception {

        Member member = setUpMember();
        String email = member.getUserMail();
        MockHttpSession session = new MockHttpSession();
        memberService.join(member);


        MemberApiController.LoginMemberRequest loginMemberRequest = new MemberApiController.LoginMemberRequest(email, RAW_PASSWORD);
        this.mockMvc.perform(post("/api/member/login")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginMemberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.login").value(Boolean.TRUE));

        this.mockMvc.perform(delete("/api/member")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(Boolean.TRUE));

    }

    public Member setUpMember(){
        Member member = new Member();
        String email = "shseoul14@gmail.com";
        String psw = RAW_PASSWORD;
        String name = "sehyeong";
        member.setUsername(name);
        member.setPassword(passwordEncoder.encode(psw));
        member.setUserMail(email);
        return member;
    }

}