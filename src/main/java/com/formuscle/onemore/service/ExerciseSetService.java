package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.ExerciseSet;
import com.formuscle.onemore.repository.ExerciseSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExerciseSetService {

    private final ExerciseSetRepository exerciseSetRepository;

    public Long save(ExerciseSet exerciseSet){
        exerciseSetRepository.save(exerciseSet);
        return exerciseSet.getId();
    }

    public List<ExerciseSet> findExerciseSets() {return exerciseSetRepository.findAll();}
    public List<ExerciseSet> findExerciseSetsByTemplateId(Long templateId) {return exerciseSetRepository.findAllByTemplateId(templateId);}
    public ExerciseSet findOne(Long exerciseId) {return exerciseSetRepository.findOne(exerciseId);}

}
