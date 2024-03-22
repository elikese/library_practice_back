package com.study.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuth2 {
    private int oauth2Id;
    private String oauth2Name;
    private int userId;
    private String providerName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
