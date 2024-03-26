package com.study.library.dto;

import com.study.library.entity.Book;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterBookReqDto {
    private String isbn;
    @Min(value = 1, message = "숫자만 입력 가능합니다") // 정수자료형에서만 @Min 사용가능. 최소값설정을 value 값으로 잡아준다
    private int bookTypeId;
    @Min(value = 1, message = "숫자만 입력 가능합니다")
    private int categoryId;
    @NotBlank(message = "도서명은 빈 값일 수 없습니다")
    private String bookName;
    @NotBlank(message = "저자명은 빈 값일 수 없습니다")
    private String authorName;
    @NotBlank(message = "출판사명은 빈 값일 수 없습니다")
    private String publisherName;
    @NotBlank(message = "커버 이미지 URL은 빈 값일 수 없습니다")
    private String coverImgUrl;

    public Book toEntity() {
        return Book.builder()
                .isbn(isbn)
                .bookName(bookName)
                .bookTypeId(bookTypeId)
                .categoryId(categoryId)
                .authorName(authorName)
                .publisherName(publisherName)
                .coverImgUrl(coverImgUrl)
                .build();
    }
}
