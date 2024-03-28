package com.study.library.controller.admin;

import com.study.library.aop.annotation.ParamPrintAspect;
import com.study.library.aop.annotation.ValidAspect;
import com.study.library.dto.RegisterBookReqDto;
import com.study.library.dto.SearchBookReqDto;
import com.study.library.dto.UpdateBookReqDto;
import com.study.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    @ValidAspect
    public ResponseEntity<?> saveBook(@Valid @RequestBody RegisterBookReqDto reqDto, BindingResult bindingResult) {
        bookService.saveBook(reqDto);
        return ResponseEntity.created(null).body(true);
    }

    @GetMapping("books")
    public ResponseEntity<?> searchBook(SearchBookReqDto reqDto) {
        return ResponseEntity.ok(bookService.searchBooks(reqDto));
    }

    @GetMapping("/books/count")
    public ResponseEntity<?> getCount(SearchBookReqDto reqDto) {
        return ResponseEntity.ok(bookService.getBookCount(reqDto));
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable int bookId) {
        return ResponseEntity.ok(null);
    }

    @ParamPrintAspect
    @DeleteMapping("/books")
    public ResponseEntity<?> deleteBooks(@RequestBody List<Integer> bookIds) {
        bookService.deleteBooks(bookIds);
        return ResponseEntity.ok(true);
    }

    @ParamPrintAspect
    @PutMapping("/book/{bookId}")
    public ResponseEntity<?> updateBook(
            @PathVariable int bookId,
            @RequestBody UpdateBookReqDto reqDto) {
        bookService.updateBook(reqDto);
        return ResponseEntity.ok(true);
    }
}