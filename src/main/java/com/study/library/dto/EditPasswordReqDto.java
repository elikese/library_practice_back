package com.study.library.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class EditPasswordReqDto {

    @NotBlank
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{7,128}$", message = "영문자, 숫자, 특수문자 포함하여 8~128자리")
    private String newPassword;

    @NotBlank
    private String newPasswordCheck;
}
