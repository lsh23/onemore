package com.formuscle.onemore.repository;

import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TrainingExerciseRepository {
    
    @PersistenceContext
    private EntityManager em;

    public void save(TrainingExercise muscleTarget){
        em.persist(muscleTarget);
    }

    public List<TrainingExercise> findAll(){
        return em.createQuery("select t from TrainingExercise t",TrainingExercise.class)
                .getResultList();
    }

    public List<TrainingExercise> findByName(String name) {
        return em.createQuery("select t from TrainingExercise t where t.trainingExerciseName =: name",TrainingExercise.class)
                .setParameter("name",name)
                .getResultList();
    }

    public List<TrainingExercise> findAllByTarget(Long targetId) {
        return em.createQuery("select t from TrainingExercise t where t.muscleTarget.id =: targetId",TrainingExercise.class)
                .setParameter("targetId",targetId)
                .getResultList();
    }

    public TrainingExercise findOne(Long id){
        return em.find(TrainingExercise.class,id);
    }


}
