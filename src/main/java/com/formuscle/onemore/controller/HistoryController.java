package com.formuscle.onemore.controller;

import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.service.HistoryService;
import com.formuscle.onemore.service.TrainingExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final TrainingExerciseService trainingExerciseService;

    @RequestMapping("/history")
    public String history(Model model){
        model.addAttribute("exercises",trainingExerciseService.findTrainingExercises());
        return "histories/history";
    }

}
