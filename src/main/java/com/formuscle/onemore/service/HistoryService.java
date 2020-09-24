package com.formuscle.onemore.service;

import com.formuscle.onemore.domain.History;
import com.formuscle.onemore.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional
    public Long save(History history){
        historyRepository.save(history);
        return history.getId();
    }

    public List<History> findHistories() {return historyRepository.findAll();}
    public List<History> findHistoriesByExercise(Long exerciseId) {return historyRepository.findAllByExercise(exerciseId);}
    public List<History> findHistoriesByTarget(Long targetId) {return historyRepository.findAllByTarget(targetId);}

    public History findOne(Long historyId){
        return historyRepository.findOne(historyId);
    }
}
