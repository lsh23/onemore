package com.formuscle.onemore.repository;

import com.formuscle.onemore.domain.MuscleTarget;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MuscleTargetRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(MuscleTarget muscleTarget){
        em.persist(muscleTarget);
    }

    public List<MuscleTarget> findAll(){
        return em.createQuery("select m from MuscleTarget m",MuscleTarget.class)
                .getResultList();
    }

    public List<MuscleTarget> findByName(String name) {
        return em.createQuery("select m from MuscleTarget m where m.muscleTargetName =: name",MuscleTarget.class)
                .setParameter("name",name)
                .getResultList();
    }

    public MuscleTarget findOne(Long id){
        return em.find(MuscleTarget.class,id);
    }


}
