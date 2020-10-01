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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        Float weight = Float.valueOf(request.weight);
        LocalDateTime now = LocalDateTime.now();

        history.setWeight(weight);
        history.setTimes(Integer.valueOf(request.setTimes));
        history.setLocalDateTime(now);

        historyService.save(history);

        return new HistoryDto(trainingExercise.getTrainingExerciseName(), weight, now);
    }

    @GetMapping("/api/histories")
    public List<HistoryDto> allHistories(){

        List<History> histories = historyService.findHistories();
        List<HistoryDto> historyDtoList = histories.stream()
                .map(history -> new HistoryDto(history.getTrainingExercise().getTrainingExerciseName(),
                                               history.getWeight(),
                                               history.getLocalDateTime()))
                .collect(Collectors.toList());

        return historyDtoList;
    }



    @Data
    @AllArgsConstructor
    static class HistoryDto {
        String exerciseName;
        Float weight;
        LocalDateTime localDateTime;
    }

    @Data
    @AllArgsConstructor
    static class CreateHistoryRequest {

        private String exerciseName;
        private String weight;
        private String setTimes;

    }
}
