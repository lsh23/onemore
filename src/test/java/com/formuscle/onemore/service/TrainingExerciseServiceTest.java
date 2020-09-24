package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.repository.TrainingExerciseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TrainingExerciseServiceTest {
    @Autowired
    TrainingExerciseService trainingExerciseService;
    @Autowired
    TrainingExerciseRepository trainingExerciseRepository;

    @Autowired MuscleTargetService muscleTargetService;

    @Test
//    @Rollback(false)
    public void 운동종목추가() throws Exception {
        // Given
        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);

        // When
        Long savedId = trainingExerciseService.join(trainingExercise);


        // Then
        assertEquals(trainingExercise, trainingExerciseRepository.findOne(savedId));
    }

    @Test
//    @Rollback(false)
    public void 모든_운동종목_조회() throws Exception {
        // Given
        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);

        TrainingExercise trainingExercise_1 = new TrainingExercise();
        trainingExercise_1.setTrainingExerciseName("인클라인_벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise_1);

        // When
        Long savedId = trainingExerciseService.join(trainingExercise);
        Long savedId_1 = trainingExerciseService.join(trainingExercise_1);

        // Then
        assertEquals(trainingExercise, trainingExerciseRepository.findAll().get(0));
        assertEquals(trainingExercise_1, trainingExerciseRepository.findAll().get(1));
    }


    @Test
//    @Rollback(false)
    public void 운동종목_운동부위로_조회() throws Exception {
        // Given
        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);

        TrainingExercise trainingExercise_1 = new TrainingExercise();
        trainingExercise_1.setTrainingExerciseName("인클라인_벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise_1);

        // When
        Long savedId = trainingExerciseService.join(trainingExercise);
        Long savedId_1 = trainingExerciseService.join(trainingExercise_1);

        // Then
        assertEquals(trainingExercise, trainingExerciseRepository.findAllByTarget(targetId).get(0));
        assertEquals(trainingExercise_1, trainingExerciseRepository.findAllByTarget(targetId).get(1));
    }
}