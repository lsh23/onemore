package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class MuscleTarget {
    @Id
    @GeneratedValue
    @Column(name = "muscle_target_id")
    private Long id;
    private String muscleTargetName;

    @OneToMany(mappedBy = "muscleTarget",cascade = CascadeType.ALL)
    private List<TrainingExercise> trainingExercises = new ArrayList<>();

    public void addTrainingExercise(TrainingExercise trainingExercise){
        trainingExercises.add(trainingExercise);
        trainingExercise.setMuscleTarget(this);
    }
}
