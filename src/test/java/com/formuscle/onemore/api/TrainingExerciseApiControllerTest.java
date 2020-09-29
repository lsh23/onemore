package com.formuscle.onemore.api;

import com.formuscle.onemore.controller.MuscleTargetController;
import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.service.MuscleTargetService;
import com.formuscle.onemore.service.TrainingExerciseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.transaction.annotation.Transactional;




@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrainingExerciseApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MuscleTargetService muscleTargetService;

    @Autowired
    private TrainingExerciseService trainingExerciseService;

    @Test
    @Transactional
    public void 운동_ID로_조회() throws Exception{
        //given
        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);
        trainingExerciseService.join(trainingExercise);

        this.mockMvc.perform(get("/api/exercise/"+trainingExercise.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exerciseName").value(trainingExercise.getTrainingExerciseName()))
                .andExpect(jsonPath("$.targetName").value(muscleTarget.getMuscleTargetName()));

    }

}