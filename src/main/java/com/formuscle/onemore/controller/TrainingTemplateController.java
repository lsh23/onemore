package com.formuscle.onemore.controller;

import com.formuscle.onemore.domain.ExerciseSet;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.domain.TrainingTemplate;
import com.formuscle.onemore.service.TrainingExerciseService;
import com.formuscle.onemore.service.TrainingTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainingTemplateController {

    private final TrainingExerciseService trainingExerciseService;
    private final TrainingTemplateService trainingTemplateService;
    private List<ExerciseSet> exerciseSets = new ArrayList<>();

    @GetMapping("/workout")
    public String workOut(Model model){
        model.addAttribute("exercises",trainingExerciseService.findTrainingExercises());
        return "workout";
    }

    @PostMapping("/template/")
    public String load(@RequestParam("templateId") Long templateId,
                         Model model){
        model.addAttribute("exercises",trainingExerciseService.findTrainingExercises());
        TrainingTemplate trainingTemplate = trainingTemplateService.findOne(templateId);
        model.addAttribute("sets",trainingTemplate.getExerciseSets());
        return "workout";
    }

}
