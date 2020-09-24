package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.repository.MuscleTargetRepository;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class MuscleTargetServiceTest {
    @Autowired MuscleTargetService muscleTargetService;
    @Autowired MuscleTargetRepository muscleTargetRepository;

    @Test
    public void 운동부위추가 () throws Exception {
        // Given
        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");

        // When
        Long savedId = muscleTargetService.join(muscleTarget);
        // Then

        assertEquals(muscleTarget, muscleTargetRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_운동부위_예외() throws Exception {
        // Given
        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName("가슴");

        MuscleTarget muscleTarget_2 = new MuscleTarget();
        muscleTarget_2.setMuscleTargetName("가슴");

        // when
        muscleTargetService.join(muscleTarget);
        muscleTargetService.join(muscleTarget_2);

        // then
        fail("예외가 발생해야 한다.");
    }
}