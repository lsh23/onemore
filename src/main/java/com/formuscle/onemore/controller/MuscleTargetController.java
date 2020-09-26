package com.formuscle.onemore.controller;

import com.formuscle.onemore.domain.MuscleTarget;
import com.formuscle.onemore.service.MuscleTargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MuscleTargetController {

    private final MuscleTargetService muscleTargetService;

    @GetMapping("/targets/new")
    public String createForm(Model model){
        model.addAttribute("targetForm",new TargetForm());
        return "targets/createTargetForm";
    }

    @PostMapping("/targets/new")
    public String create(TargetForm targetForm, BindingResult result){

        if(result.hasErrors()){
            return "targets/createTargetForm";
        }

        MuscleTarget muscleTarget = new MuscleTarget();
        muscleTarget.setMuscleTargetName(targetForm.getTargetName());
        muscleTargetService.join(muscleTarget);
        return "redirect:/home";
    }

    @GetMapping("/targets")
    public String list(Model model){
        model.addAttribute("targets",muscleTargetService.findMuscleTargets());
        return "targets/targetList";
    }
}
