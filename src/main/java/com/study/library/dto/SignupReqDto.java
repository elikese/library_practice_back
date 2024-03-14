package com.study.library.dto;

import com.study.library.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignupReqDto {

    @Pattern(regexp = "^[a-z]{1}[a-z0-9_]{3,10}+$", message = "영문 숫자 조합 4~10자리여야 합니다")
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$", message = "대소문자, 숫자, 특수문자 조합으로 8~128자리여야 합니다")
    private String password;

    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣]{2,}$", message = "한글만 입력하세요")
    private String name;

    @Email
    @NotBlank
    private String email;

    public User toEntity() {

        return User.builder()
                .userName(username)
                .password(null)
                .name(name)
                .email(email)
                .build();
    }
}