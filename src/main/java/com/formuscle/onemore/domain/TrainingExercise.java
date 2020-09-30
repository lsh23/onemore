package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@SequenceGenerator(
        name= "EXERCISE_SEQ_GENERATOR",
        sequenceName = "EXERCISE_SEQ",
        initialValue = 1, allocationSize = 1
)
@Getter @Setter
@NoArgsConstructor
public class TrainingExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "EXERCISE_SEQ_GENERATOR")
    @Column(name = "training_exercise_id")
    private Long id;
    private String trainingExerciseName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "muscle_target_id")
    private MuscleTarget muscleTarget;
}
