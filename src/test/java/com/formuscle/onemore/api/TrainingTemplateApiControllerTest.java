package com.formuscle.onemore.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formuscle.onemore.domain.ExerciseSet;
import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.domain.TrainingTemplate;
import com.formuscle.onemore.service.ExerciseSetService;
import com.formuscle.onemore.service.MuscleTargetService;
import com.formuscle.onemore.service.TrainingExerciseService;
import com.formuscle.onemore.service.TrainingTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private ExerciseSetService exerciseSetService;

    @Autowired
    private TrainingTemplateService trainingTemplateService;

    @Autowired
    private EntityManager em;

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

    @Test
    @Transactional
    public void 모든_템플릿명_조회() throws Exception{
        //given
        setUpForGetTest();

        this.mockMvc.perform(get("/api/templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].templateName",is("가슴운동_1")))
                .andExpect(jsonPath("$[1].templateName",is("가슴운동_2")));

    }

    @Test
    @Transactional
    public void 운동세트_템플릿이름으로_조회() throws Exception{
        //given
        setUpForGetTest();

        this.mockMvc.perform(get("/api/templates/가슴운동_1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].exerciseName").value("벤치프레스"))
                .andExpect(jsonPath("$[0].targetName").value("가슴"))
                .andExpect(jsonPath("$[1].exerciseName").value("벤치프레스"))
                .andExpect(jsonPath("$[1].targetName").value("가슴"));


        this.mockMvc.perform(get("/api/templates/가슴운동_2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].exerciseName").value("벤치프레스"))
                .andExpect(jsonPath("$[0].targetName").value("가슴"))
                .andExpect(jsonPath("$[1].exerciseName").value("벤치프레스"))
                .andExpect(jsonPath("$[1].targetName").value("가슴"))
                .andExpect(jsonPath("$[2].exerciseName").value("벤치프레스"))
                .andExpect(jsonPath("$[2].targetName").value("가슴"));
    }

    private void setUpForGetTest() {
        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);
        trainingExerciseService.join(trainingExercise);

        TrainingTemplate trainingTemplate = new TrainingTemplate();
        for(int i=0;i<2;i++) {
            ExerciseSet exerciseSet = new ExerciseSet();
            exerciseSet.setTrainingExercise(trainingExercise);
            trainingTemplate.addExerciseSet(exerciseSet);
            exerciseSetService.save(exerciseSet);
        }
        TrainingTemplate trainingTemplate_1 = new TrainingTemplate();
        for(int i=0;i<3;i++) {
            ExerciseSet exerciseSet = new ExerciseSet();
            exerciseSet.setTrainingExercise(trainingExercise);
            trainingTemplate_1.addExerciseSet(exerciseSet);
            exerciseSetService.save(exerciseSet);
        }

        trainingTemplate.setTrainingTemplateName("가슴운동_1");
        trainingTemplateService.save(trainingTemplate);
        trainingTemplate_1.setTrainingTemplateName("가슴운동_2");
        trainingTemplateService.save(trainingTemplate_1);
    }


}