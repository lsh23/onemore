package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class History {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "training_exercise_id")
    private TrainingExercise trainingExercise;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "muscle_target_id")
    private MuscleTarget muscleTarget;

    private Float weight;
    private Integer times;
    private Boolean success;
    private LocalDateTime localDateTime;
}
