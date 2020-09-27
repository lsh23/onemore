package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.*;
import com.formuscle.onemore.repository.ExerciseSetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ExerciseSetServiceTest {

    @Autowired
    ExerciseSetService exerciseSetService;
    @Autowired
    ExerciseSetRepository exerciseSetRepository;
    @Autowired
    TrainingTemplateService trainingTemplateService;
    @Autowired
    TrainingExerciseService trainingExerciseService;
    @Autowired
    MuscleTargetService muscleTargetService;
    @Autowired
    MemberService memberService;

    @Test
//    @Rollback(false)
    public void 운동세트_저장() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        TrainingTemplate trainingTemplate = new TrainingTemplate();
        trainingTemplate.setTrainingTemplateName("등/가슴");
        trainingTemplate.setMember(member);
        Long templateId = trainingTemplateService.save(trainingTemplate);

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);

        Long exerciseId = trainingExerciseService.join(trainingExercise);

        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setSetTimes(5);
        exerciseSet.setTrainingExercise(trainingExercise);
        exerciseSet.setTrainingTemplate(trainingTemplate);

        // When
        Long setId = exerciseSetService.save(exerciseSet);

        // Then
        assertEquals(exerciseSet, exerciseSetRepository.findOne(setId));
    }

    @Test
//    @Rollback(false)
    public void 운동세트_템플릿ID로_조회() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        TrainingTemplate trainingTemplate = new TrainingTemplate();
        trainingTemplate.setTrainingTemplateName("등/가슴");
        trainingTemplate.setMember(member);
        Long templateId = trainingTemplateService.save(trainingTemplate);

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);

        TrainingExercise trainingExercise_1 = new TrainingExercise();
        trainingExercise_1.setTrainingExerciseName("인클라인_벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise_1);

        Long exerciseId = trainingExerciseService.join(trainingExercise);
        Long exerciseId_1 = trainingExerciseService.join(trainingExercise_1);

        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setSetTimes(5);
        exerciseSet.setTrainingExercise(trainingExercise);
        exerciseSet.setTrainingTemplate(trainingTemplate);

        ExerciseSet exerciseSet_1 = new ExerciseSet();
        exerciseSet_1.setSetTimes(5);
        exerciseSet_1.setTrainingExercise(trainingExercise_1);
        exerciseSet_1.setTrainingTemplate(trainingTemplate);

        // When
        Long setId = exerciseSetService.save(exerciseSet);
        Long setId_1 = exerciseSetService.save(exerciseSet_1);

        // Then
        assertEquals(exerciseSet, exerciseSetRepository.findAll().get(0));
        assertEquals(exerciseSet_1, exerciseSetRepository.findAll().get(1));
        assertEquals(exerciseSet, exerciseSetRepository.findOne(setId));
        assertEquals(exerciseSet_1, exerciseSetRepository.findOne(setId_1));
        assertEquals(exerciseSet, exerciseSetRepository.findAllByTemplateId(templateId).get(0));
        assertEquals(exerciseSet_1, exerciseSetRepository.findAllByTemplateId(templateId).get(1));
    }

    @Test
//    @Rollback(false)
    public void 운동세트_수정() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        TrainingTemplate trainingTemplate = new TrainingTemplate();
        trainingTemplate.setTrainingTemplateName("등/가슴");
        trainingTemplate.setMember(member);
        Long templateId = trainingTemplateService.save(trainingTemplate);

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);

        TrainingExercise trainingExercise_1 = new TrainingExercise();
        trainingExercise_1.setTrainingExerciseName("인클라인_벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise_1);

        Long exerciseId = trainingExerciseService.join(trainingExercise);
        Long exerciseId_1 = trainingExerciseService.join(trainingExercise_1);

        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setSetTimes(5);
        exerciseSet.setTrainingExercise(trainingExercise);
        exerciseSet.setTrainingTemplate(trainingTemplate);

        ExerciseSet exerciseSet_1 = new ExerciseSet();
        exerciseSet_1.setSetTimes(5);
        exerciseSet_1.setTrainingExercise(trainingExercise_1);
        exerciseSet_1.setTrainingTemplate(trainingTemplate);

        Long setId = exerciseSetService.save(exerciseSet);
        Long setId_1 = exerciseSetService.save(exerciseSet_1);

        // When
        ExerciseSet one = exerciseSetService.findOne(setId);
        one.setSetTimes(3);

        // Then
        assertEquals(one, exerciseSetRepository.findOne(setId));
    }
}