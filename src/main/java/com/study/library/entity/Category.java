package com.study.library.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private int categoryId;
    private String categoryName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
