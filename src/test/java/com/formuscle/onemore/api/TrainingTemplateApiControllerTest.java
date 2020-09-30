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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrainingTemplateApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrainingExerciseService trainingExerciseService;

    @Autowired
    private MuscleTargetService muscleTargetService;

    @Test
    @Transactional
    public void template_저장() throws Exception {

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        muscleTargetService.join(muscleTarget);
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        trainingExercise.setMuscleTarget(muscleTarget);
        trainingExerciseService.join(trainingExercise);

        String templateName = "test";
        List<String> exerciseNames = new ArrayList<>();
        exerciseNames.add("벤치프레스");
        List<String> targetNames = new ArrayList<>();
        targetNames.add("가슴");
        TrainingTemplateApiController.CreateTemplateRequest createTemplateRequest = new TrainingTemplateApiController.CreateTemplateRequest(templateName,exerciseNames,targetNames);


        this.mockMvc.perform(post("/api/templates/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createTemplateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.templateName").value(createTemplateRequest.getTemplateName()));

    }


}