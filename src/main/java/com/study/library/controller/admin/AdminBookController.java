package com.study.library.controller.admin;

import com.study.library.aop.annotation.ParamPrintAspect;
import com.study.library.aop.annotation.ValidAspect;
import com.study.library.dto.RegisterBookReqDto;
import com.study.library.dto.SearchBookReqDto;
import com.study.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    @ParamPrintAspect
    @ValidAspect
    public ResponseEntity<?> saveBook(@Valid @RequestBody RegisterBookReqDto reqDto, BindingResult bindingResult) {
        bookService.saveBook(reqDto);
        return ResponseEntity.created(null).body(true);
    }

    @GetMapping("books")
    @ParamPrintAspect
    public ResponseEntity<?> searchBook(SearchBookReqDto reqDto) {
        return ResponseEntity.ok(bookService.searchBooks(reqDto));
    }

}