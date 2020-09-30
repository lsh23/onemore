package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.repository.MuscleTargetRepository;
import com.formuscle.onemore.repository.TrainingExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainingExerciseService {
    private final TrainingExerciseRepository trainingExerciseRepository;

    @Transactional
    public Long join(TrainingExercise trainingExercise){
        trainingExerciseRepository.save(trainingExercise);
        return trainingExercise.getId();
    }

    public List<TrainingExercise> findTrainingExercises(){
        return trainingExerciseRepository.findAll();
    }

    public List<TrainingExercise> findTrainingExercisesByTarget(Long targetId){
        return trainingExerciseRepository.findAllByTarget(targetId);
    }

    public TrainingExercise findOne(Long trainingExerciseId){
        return trainingExerciseRepository.findOne(trainingExerciseId);
    }

    public TrainingExercise findOneByName(String trainingExerciseName){
        return trainingExerciseRepository.findOneByName(trainingExerciseName);
    }
}
