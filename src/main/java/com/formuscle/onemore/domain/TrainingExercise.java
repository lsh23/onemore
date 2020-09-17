package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
public class TrainingExercise {
    @Id
    @GeneratedValue
    @Column(name = "training_exercise_id")
    private Long id;
    private String trainingExerciseName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "muscle_target_id")
    private MuscleTarget muscleTarget;

    @OneToOne(mappedBy = "trainingExercise")
    private ExerciseSet exerciseSet;

    @OneToOne(mappedBy = "trainingExercise")
    private History history;
}
