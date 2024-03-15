package com.study.library.entity;

import com.study.library.security.PrincipalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private int userId;
    private String userName;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<Role> roles;

    public PrincipalUser toPrincipal() {
        return PrincipalUser.builder()
                .userId(userId)
                .username(userName)
                .name(name)
                .email(email)
                .build();
    }
}