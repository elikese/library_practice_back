package com.study.library.service;

import com.study.library.dto.RegisterBookReqDto;
import com.study.library.exception.SaveException;
import com.study.library.repository.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookMapper bookMapper;

    @Transactional(rollbackFor = Exception.class)
    public void saveBook(RegisterBookReqDto reqDto) {
        int successCount = bookMapper.saveBook(reqDto.toEntity());
        if(successCount < 1) {
            throw new SaveException();
        }
    }
}
