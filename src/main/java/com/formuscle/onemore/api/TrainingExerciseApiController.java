package com.formuscle.onemore.api;

import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.service.TrainingExerciseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class TrainingExerciseApiController {

    private final TrainingExerciseService trainingExerciseService;

    @GetMapping("/api/exercise/{id}")
    public ExerciseDto exerciseById(@PathVariable("id") Long id){
        TrainingExercise trainingExercise = trainingExerciseService.findOne(id);
        ExerciseDto exerciseDto = new ExerciseDto(trainingExercise.getTrainingExerciseName(),
                trainingExercise.getMuscleTarget().getMuscleTargetName());
        return exerciseDto;
    }

    @Data
    @AllArgsConstructor
    static class ExerciseDto {
        @NotEmpty
        private String exerciseName;
        @NotEmpty
        private String targetName;
    }
}
