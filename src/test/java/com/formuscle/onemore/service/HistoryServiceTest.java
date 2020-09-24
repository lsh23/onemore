package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.*;
import com.formuscle.onemore.repository.ExerciseSetRepository;
import com.formuscle.onemore.repository.HistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class HistoryServiceTest {

    @Autowired
    HistoryService historyService;
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    TrainingExerciseService trainingExerciseService;
    @Autowired
    MuscleTargetService muscleTargetService;
    @Autowired
    MemberService memberService;

    @Test
//    @Rollback(false)
    public void 기록_추가() throws Exception {
        // Given
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);


        Long exerciseId = trainingExerciseService.join(trainingExercise);

        History history = new History();
        history.setMember(member);
        history.setTrainingExercise(trainingExercise);
        history.setMuscleTarget(trainingExercise.getMuscleTarget());
        history.setLocalDateTime(LocalDateTime.now());
        history.setSuccess(true);
        history.setTimes(5);
        history.setWeight(100.0F);

        // When
        Long historyId = historyService.save(history);

        // Then
        assertEquals(history,historyRepository.findOne(historyId));
    }

    @Test
    public void 기록_운동종목으로_조회() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);


        Long exerciseId = trainingExerciseService.join(trainingExercise);
        History history = save_history(member, trainingExercise, LocalDateTime.now(), true, 5, 100.0f);
        History history_1 = save_history(member, trainingExercise, LocalDateTime.now(), true, 3, 110.0f);

        // When
        Long history_id = historyService.save(history);
        Long history_id_1 = historyService.save(history_1);

        // Then
        assertEquals(history,historyRepository.findAllByExercise(exerciseId).get(0));
        assertEquals(history_1,historyRepository.findAllByExercise(exerciseId).get(1));

    }

    @Test
    public void 기록_운동부위로_조회() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");
        Long targetId = muscleTargetService.join(muscleTarget);

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setTrainingExerciseName("벤치프레스");
        muscleTarget.addTrainingExercise(trainingExercise);


        Long exerciseId = trainingExerciseService.join(trainingExercise);
        History history = save_history(member, trainingExercise, LocalDateTime.now(), true, 5, 100.0f);
        History history_1 = save_history(member, trainingExercise, LocalDateTime.now(), true, 3, 110.0f);

        // When
        Long history_id = historyService.save(history);
        Long history_id_1 = historyService.save(history_1);

        // Then
        assertEquals(history,historyRepository.findAllByTarget(targetId).get(0));
        assertEquals(history_1,historyRepository.findAllByTarget(targetId).get(1));

    }

    private History save_history(Member member, TrainingExercise trainingExercise,LocalDateTime localDateTime, boolean success, int setTimes, float weight) {
        History history = new History();
        history.setMember(member);
        history.setTrainingExercise(trainingExercise);
        history.setMuscleTarget(trainingExercise.getMuscleTarget());
        history.setLocalDateTime(localDateTime);
        history.setSuccess(success);
        history.setTimes(setTimes);
        history.setWeight(weight);
        return history;
    }

}