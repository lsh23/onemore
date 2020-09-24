package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.repository.MuscleTargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MuscleTargetService {

    private final MuscleTargetRepository muscleTargetRepository;

    @Transactional
    public Long join(MuscleTarget muscleTarget){

        validateDuplicateMuscleTarget(muscleTarget);
        muscleTargetRepository.save(muscleTarget);
        return muscleTarget.getId();

    }

    private void validateDuplicateMuscleTarget(MuscleTarget muscleTarget){
        List<MuscleTarget> findMuscleTargets = muscleTargetRepository.findByName(muscleTarget.getMuscleTargetName());
        if(!findMuscleTargets.isEmpty()){
            throw new IllegalStateException("이미 존재하는 운동부위 입니다.");
        }
    }

    public List<MuscleTarget> findMuscleTargets(){
        return muscleTargetRepository.findAll();
    }

    public MuscleTarget findOne(Long muscleTargetId){
        return muscleTargetRepository.findOne(muscleTargetId);
    }

}
