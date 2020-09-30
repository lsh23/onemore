package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name= "TEMPLATE_SEQ_GENERATOR",
        sequenceName = "TEMPLATE_SEQ",
        initialValue = 1, allocationSize = 1
)
@Getter @Setter
public class TrainingTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "TEMPLATE_SEQ_GENERATOR")
    @Column(name = "training_template_id")
    private Long id;
    private String trainingTemplateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "trainingTemplate")
    private List<ExerciseSet> exerciseSets = new ArrayList<>();

    //연관 관계 메서드
    public void setMember(Member member){
        this.member = member;
        member.getTrainingTemplates().add(this);
    }

    public void addExerciseSet(ExerciseSet exerciseSet){
        exerciseSets.add(exerciseSet);
        exerciseSet.setTrainingTemplate(this);
    }

}
