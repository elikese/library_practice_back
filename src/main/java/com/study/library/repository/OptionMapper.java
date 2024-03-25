package com.study.library.repository;

import com.study.library.entity.BookType;
import com.study.library.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper {

    List<BookType> getAllBookTypes();
    List<Category> getAllCategories();
}
