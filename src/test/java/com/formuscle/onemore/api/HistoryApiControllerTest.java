package com.formuscle.onemore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formuscle.onemore.domain.History;
import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.service.HistoryService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private HistoryService historyService;

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

    @Test
    @Transactional
    public void 모든_기록_조회() throws Exception{
        //given
        setUpForGetTest();

        this.mockMvc.perform(get("/api/histories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].exerciseName",is("벤치프레스")))
                .andExpect(jsonPath("$[0].weight",is(65.0)));
    }

    private void setUpForGetTest() {

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        muscleTargetService.join(muscleTarget);
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        trainingExercise.setMuscleTarget(muscleTarget);
        trainingExerciseService.join(trainingExercise);

        for(int i=0;i<5;i++){
            History history = new History();
            history.setTrainingExercise(trainingExercise);
            history.setMuscleTarget(muscleTarget);
            history.setLocalDateTime(LocalDateTime.now());
            history.setTimes(5);
            history.setWeight(65.0f);
            historyService.save(history);
        }

    }


}