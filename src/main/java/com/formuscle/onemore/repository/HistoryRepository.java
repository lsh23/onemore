package com.formuscle.onemore.repository;

import com.formuscle.onemore.domain.History;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HistoryRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(History history) {
        em.persist(history);
    }

    public List<History> findAll() {
        return em.createQuery("select h from History h",History.class)
                .getResultList();
    }

    public List<History>  findAllByTarget(Long targetId) {
        return em.createQuery("select h from History h where h.muscleTarget.id =: targetId")
                .setParameter("targetId",targetId)
                .getResultList();
    }

    public List<History> findAllByExercise(Long exerciseId) {
        return em.createQuery("select h from History h where h.trainingExercise.id =: exerciseId")
                .setParameter("exerciseId",exerciseId)
                .getResultList();
    }

    public History findOne(Long historyId) {
        return em.find(History.class,historyId);
    }


}
