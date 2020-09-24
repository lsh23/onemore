package com.formuscle.onemore.repository;

import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.domain.TrainingTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TrainingTemplateRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(TrainingTemplate trainingTemplate) {
        em.persist(trainingTemplate);
    }

    public List<TrainingTemplate> findAll() {
        return em.createQuery("select t from TrainingTemplate t",TrainingTemplate.class)
                .getResultList();
    }

    public List<TrainingTemplate> findAllByMemberId(Long memberId) {
        return em.createQuery("select t from TrainingTemplate t where t.member.id=:memberId",TrainingTemplate.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }

    public TrainingTemplate findOne(Long trainingTemplateId) {
        return em.find(TrainingTemplate.class,trainingTemplateId);
    }


}
