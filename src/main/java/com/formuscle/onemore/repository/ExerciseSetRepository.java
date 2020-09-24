package com.formuscle.onemore.repository;

import com.formuscle.onemore.domain.ExerciseSet;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ExerciseSetRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ExerciseSet exerciseSet) {
        em.persist(exerciseSet);
    }

    public List<ExerciseSet> findAll() {
        return em.createQuery("select e from ExerciseSet e",ExerciseSet.class)
                .getResultList();
    }

    public List<ExerciseSet> findAllByTemplateId(Long templateId) {
        return em.createQuery("select e from ExerciseSet e where e.trainingTemplate.id =:templateId",ExerciseSet.class)
                .setParameter("templateId",templateId)
                .getResultList();
    }


    public ExerciseSet findOne(Long exerciseId) {
        return em.find(ExerciseSet.class,exerciseId);
    }
}
