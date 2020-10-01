package com.formuscle.onemore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.service.MuscleTargetService;
import com.formuscle.onemore.service.TrainingExerciseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HistoryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrainingExerciseService trainingExerciseService;

    @Autowired
    private MuscleTargetService muscleTargetService;

    @Test
    @Transactional
    public void history_저장() throws Exception {

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        muscleTargetService.join(muscleTarget);
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        trainingExercise.setMuscleTarget(muscleTarget);
        trainingExerciseService.join(trainingExercise);

        HistoryApiController.CreateHistoryRequest createHistoryRequest = new HistoryApiController.CreateHistoryRequest("벤치프레스","65.5","8");

        this.mockMvc.perform(post("/api/history/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createHistoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exerciseName").value(createHistoryRequest.getExerciseName()))
                .andExpect(jsonPath("$.weight").value(Float.valueOf(createHistoryRequest.getWeight())));

    }

}