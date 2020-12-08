package com.formuscle.onemore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name= "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 1
)
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String userMail;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<TrainingTemplate> trainingTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<History> histories = new ArrayList<>();
}
