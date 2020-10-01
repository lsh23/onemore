package com.formuscle.onemore.api;


import com.formuscle.onemore.domain.History;
import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.service.HistoryService;
import com.formuscle.onemore.service.MuscleTargetService;
import com.formuscle.onemore.service.TrainingExerciseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class HistoryApiController {

    private final HistoryService historyService;
    private final TrainingExerciseService trainingExerciseService;
    private final MuscleTargetService muscleTargetService;

    @PostMapping("/api/history/new")
    public HistoryDto saveHistory(@RequestBody @Valid CreateHistoryRequest request){

        History history = new History();

        TrainingExercise trainingExercise = trainingExerciseService.findOneByName(request.exerciseName);
        MuscleTarget muscleTarget = muscleTargetService.findOneByName(trainingExercise.getMuscleTarget().getMuscleTargetName());

        history.setTrainingExercise(trainingExercise);
        history.setMuscleTarget(muscleTarget);
        history.setWeight(Float.valueOf(request.weight));
        history.setTimes(Integer.valueOf(request.setTimes));
        history.setLocalDateTime(LocalDateTime.now());

        historyService.save(history);

        return new HistoryDto(trainingExercise.getTrainingExerciseName(),Float.valueOf(request.weight));
    }

    @Data
    @AllArgsConstructor
    static class HistoryDto {
        String exerciseName;
        Float weight;
    }

    @Data
    @AllArgsConstructor
    static class CreateHistoryRequest {

        private String exerciseName;
        private String weight;
        private String setTimes;

    }
}
