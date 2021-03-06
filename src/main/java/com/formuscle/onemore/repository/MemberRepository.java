package com.formuscle.onemore.repository;

import com.formuscle.onemore.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public Member findOne(Long memberId) {
        return em.find(Member.class,memberId);
    }

    public Member findOneByEmail(String email) {
        return em.createQuery("select m from Member m where m.userMail=:email",Member.class)
                .setParameter("email",email)
                .getSingleResult();
    }
}
