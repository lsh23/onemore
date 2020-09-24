package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.*;
import com.formuscle.onemore.repository.ExerciseSetRepository;
import com.formuscle.onemore.repository.TrainingTemplateRepository;
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
public class TrainingTemplateServiceTest {

    @Autowired
    TrainingTemplateRepository trainingTemplateRepository;
    @Autowired
    TrainingTemplateService trainingTemplateService;
    @Autowired
    MemberService memberService;

    @Test
//    @Rollback(false)
    public void 운동템플릿_추가() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        TrainingTemplate trainingTemplate = new TrainingTemplate();
        trainingTemplate.setTrainingTemplateName("등/가슴");
        trainingTemplate.setMember(member);

        // When
        Long templateId = trainingTemplateService.save(trainingTemplate);

        // Then
        assertEquals(trainingTemplate, trainingTemplateRepository.findOne(templateId));
    }

    @Test
    public void 운동템플릿_멤버로_조회() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("Sehyeong");
        Long memberId = memberService.join(member);

        TrainingTemplate trainingTemplate = new TrainingTemplate();
        trainingTemplate.setTrainingTemplateName("등/가슴");
        trainingTemplate.setMember(member);

        TrainingTemplate trainingTemplate_1 = new TrainingTemplate();
        trainingTemplate_1.setTrainingTemplateName("하체_스쿼트_베이스");
        trainingTemplate_1.setMember(member);

        // When
        Long templateId = trainingTemplateService.save(trainingTemplate);
        Long templateId_1 = trainingTemplateService.save(trainingTemplate_1);

        // Then
        assertEquals(trainingTemplate, trainingTemplateRepository.findAllByMemberId(memberId).get(0));
        assertEquals(trainingTemplate_1, trainingTemplateRepository.findAllByMemberId(memberId).get(1));
    }
}