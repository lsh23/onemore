package com.formuscle.onemore.api;

import com.formuscle.onemore.domain.ExerciseSet;
import com.formuscle.onemore.domain.TrainingExercise;
import com.formuscle.onemore.domain.TrainingTemplate;
import com.formuscle.onemore.service.ExerciseSetService;
import com.formuscle.onemore.service.TrainingExerciseService;
import com.formuscle.onemore.service.TrainingTemplateService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class TrainingTemplateApiController {

    private final TrainingTemplateService trainingTemplateService;
    private final TrainingExerciseService trainingExerciseService;
    private final ExerciseSetService exerciseSetService;

    @PostMapping("/api/templates/new")
    @Transactional
    public CreateTemplateResponse saveTemplate(@RequestBody @Valid CreateTemplateRequest request){

        TrainingTemplate trainingTemplate = new TrainingTemplate();
        trainingTemplate.setTrainingTemplateName(request.templateName);
        trainingTemplateService.save(trainingTemplate);

        List<ExerciseDto> exerciseDtoList = new ArrayList<>();

        for(int i=0;i<request.targetNames.size();i++){
            ExerciseDto exerciseDto = new ExerciseDto(request.exerciseNames.get(i),request.targetNames.get(i));
            TrainingExercise trainingExercise = trainingExerciseService.findOneByName(request.exerciseNames.get(i));
            exerciseDtoList.add(exerciseDto);

            ExerciseSet exerciseSet = new ExerciseSet();
            exerciseSet.setTrainingExercise(trainingExercise);
            trainingTemplate.addExerciseSet(exerciseSet);
            exerciseSetService.save(exerciseSet);
        }

        return new CreateTemplateResponse(request.templateName,exerciseDtoList);
    }

    @GetMapping("/api/templates")
    public List<TemplateDto> allTemplates(){

        List<TrainingTemplate> trainingTemplates = trainingTemplateService.findTrainingTemplates();
        List<TemplateDto> templateDtoList = trainingTemplates.stream()
                .map(trainingTemplate -> new TemplateDto(trainingTemplate.getTrainingTemplateName()))
                .collect(Collectors.toList());

        return templateDtoList;
    }

    @GetMapping("/api/templates/{templateName}")
    public List<ExerciseDto> exercisesOfTemplate(@PathVariable("templateName") String name) throws UnsupportedEncodingException {

        name = java.net.URLDecoder.decode(name,"UTF-8");
        TrainingTemplate trainingTemplate = trainingTemplateService.findOneByName(name);
        List<ExerciseSet> exerciseSets = trainingTemplate.getExerciseSets();
        List<ExerciseDto> exerciseDtoList = exerciseSets.stream()
                .map(exerciseSet -> new ExerciseDto(exerciseSet.getTrainingExercise().getTrainingExerciseName(),
                                                    exerciseSet.getTrainingExercise().getMuscleTarget().getMuscleTargetName()))
                .collect(Collectors.toList());

        return exerciseDtoList;
    }

    @Data
    @AllArgsConstructor
    static class CreateTemplateResponse {
        private String templateName;
        private List<ExerciseDto> exerciseDtoList;
    }

    @Data
    @AllArgsConstructor
    static class CreateTemplateRequest {
        private String templateName;
        private List<String> exerciseNames;
        private List<String> targetNames;
    }

    @Data
    @AllArgsConstructor
    static class ExerciseDto{
        private String exerciseName;
        private String targetName;
    }

    @Data
    @AllArgsConstructor
    static class TemplateDto {
        private String templateName;
    }
}
