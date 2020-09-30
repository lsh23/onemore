package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name= "SET_SEQ_GENERATOR",
        sequenceName = "SET_SEQ",
        initialValue = 1, allocationSize = 1
)
@Getter @Setter
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SET_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_template_id")
    private TrainingTemplate trainingTemplate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "training_exercise_id")
    private TrainingExercise trainingExercise;

    private Integer setTimes;
}
