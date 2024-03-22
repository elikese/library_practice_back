package com.study.library.dto;

import com.study.library.entity.OAuth2;
import com.study.library.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class OAuth2SignupReqDto {

    @Pattern(regexp = "^[A-Za-z0-9]{4,10}$", message = "영문자, 숫자포함하여 5~10자리")
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{7,128}$", message = "영문자, 숫자, 특수문자 포함하여 8~128자리")
    private String password;

    @Pattern(regexp = "^(?=.*[가-힇])^[가-힇]{1,10}$", message = "한글만 입력 할 수 있습니다")
    private String name;

    @Email(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "이메일 형식이어야 합니다")
    @NotBlank
    private String email;

    @NotBlank
    private String oauth2Name;

    @NotBlank
    private String providerName;

    public User toEntity() {

        return User.builder()
                .userName(username)
                .password(null)
                .name(name)
                .email(email)
                .build();
    }

    public OAuth2 toOAuth2Entity(int userId) {
        return OAuth2.builder()
                .oauth2Name(oauth2Name)
                .userId(userId)
                .providerName(providerName)
                .build();
    }
}