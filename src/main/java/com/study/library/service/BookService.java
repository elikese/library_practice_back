package com.study.library.service;

import com.study.library.dto.RegisterBookReqDto;
import com.study.library.dto.SearchBookReqDto;
import com.study.library.dto.SearchBookResDto;
import com.study.library.entity.Book;
import com.study.library.exception.SaveException;
import com.study.library.repository.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<SearchBookResDto> searchBooks(SearchBookReqDto reqDto) {
        int startIndex = ((reqDto.getPage() - 1) * reqDto.getCount());
        List<Book> books = bookMapper.findBooks(
                startIndex,
                reqDto.getCount(),
                reqDto.getBookTypeId(),
                reqDto.getCategoryId(),
                reqDto.getSearchTypeId(),
                reqDto.getSearchText()
        );
        return books.stream().map(Book::toSearchBookResDto).collect(Collectors.toList());
    }
}
