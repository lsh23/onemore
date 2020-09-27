package com.formuscle.onemore.controller;

import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.service.MuscleTargetService;
import com.formuscle.onemore.service.TrainingExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainingExerciseController {

    private final TrainingExerciseService trainingExerciseService;
    private final MuscleTargetService muscleTargetService;

    @GetMapping("/exercises/new")
    public String createForm(Model model){
        List<MuscleTarget> targets = muscleTargetService.findMuscleTargets();
        model.addAttribute("targets",targets);
        return "exercises/createExerciseForm";
    }

    @PostMapping("/exercises/new")
    public String create(@RequestParam("targetId") Long targetId,
                         @RequestParam("exerciseName") String exerciseName){
        MuscleTarget muscleTarget = muscleTargetService.findOne(targetId);
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setMuscleTarget(muscleTarget);
        trainingExercise.setTrainingExerciseName(exerciseName);
        trainingExerciseService.join(trainingExercise);
        return "redirect:/exercises";
    }

    @GetMapping("/exercises")
    public String exerciseList(Model model){
        List<MuscleTarget> targets = muscleTargetService.findMuscleTargets();
        model.addAttribute("exercises",trainingExerciseService.findTrainingExercises());
        return "exercises/exerciseList";
    }

}
