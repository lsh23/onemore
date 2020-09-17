package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class TrainingTemplate {
    @Id
    @GeneratedValue
    @Column(name = "training_template_id")
    private Long id;
    private String trainingTemplateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "trainingTemplate")
    private List<ExerciseSet> exerciseSets = new ArrayList<>();
}
