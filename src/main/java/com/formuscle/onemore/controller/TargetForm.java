package com.formuscle.onemore.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter @Setter
public class TargetForm {

    @NotEmpty(message = "운동 부위 이름은 필수 입니다.")
    String targetName;

}
