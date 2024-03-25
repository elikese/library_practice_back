package com.study.library.service;

import com.study.library.entity.BookType;
import com.study.library.entity.Category;
import com.study.library.repository.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionsService {

    @Autowired
    private OptionMapper optionMapper;

    public List<BookType> getAllBookTypes() {
        return optionMapper.getAllBookTypes();
    }

    public List<Category> getAllCategories() {
        return optionMapper.getAllCategories();
    }
}
