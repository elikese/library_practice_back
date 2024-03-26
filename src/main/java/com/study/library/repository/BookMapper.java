package com.study.library.repository;

import com.study.library.entity.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    int saveBook(Book book);
}
