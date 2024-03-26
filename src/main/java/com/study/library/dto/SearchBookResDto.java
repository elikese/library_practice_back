package com.study.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookResDto {

    private int bookId;
    private String bookName;
    private String authorName;
    private String publisherName;
    private int bookTypeId;
    private String bookTypeName;
    private String isbn;
    private int categoryId;
    private String categoryName;
    private String coverImgUrl;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


}
