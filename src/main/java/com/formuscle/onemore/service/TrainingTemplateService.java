package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.domain.TrainingTemplate;
import com.formuscle.onemore.repository.TrainingExerciseRepository;
import com.formuscle.onemore.repository.TrainingTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainingTemplateService {

    private final TrainingTemplateRepository trainingTemplateRepository;

    public Long save (TrainingTemplate trainingTemplate){
        trainingTemplateRepository.save(trainingTemplate);
        return trainingTemplate.getId();
    }

    public List<TrainingTemplate> findTrainingTemplates() {
        return trainingTemplateRepository.findAll();
    }

    public List<TrainingTemplate> findTrainingTemplatesByMemberId(Long memberId) {
        return trainingTemplateRepository.findAllByMemberId(memberId);
    }

    public TrainingTemplate findOne(Long trainingTemplateId){
        return trainingTemplateRepository.findOne(trainingTemplateId);
    }




}
