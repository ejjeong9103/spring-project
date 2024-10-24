package com.estsoft.springproject.example.controller;

import com.estsoft.springproject.example.entity.Book;
import com.estsoft.springproject.example.entity.BookDTO;
import com.estsoft.springproject.example.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    //  GET /books
    @GetMapping
    public String showAll(Model model) {

        List<BookDTO> list = bookService.findAll()
                .stream()
                .map(BookDTO::new)
                .toList();
        model.addAttribute("bookList", list);
        return "bookManagement";
    }

    // GET /books/{id}   단권 조회
    @GetMapping("/{id}")
    public String showOne(@PathVariable String id, Model model) {
        Book book = bookService.findBy(id);
        model.addAttribute("book",new BookDTO(book));
        return "bookDetail";
    }

//     POST /books/{id}
    // id, name, author 정보 받아서 DB에 저장,
    // 저장된 정보가 바로 노출될수 있도록 화면 구성(bookManagement.html)
    @PostMapping
    public String addBook(@RequestParam String id,
                          @RequestParam String name,
                          @RequestParam String author) { // @RequestParam -> html에 입력된 값을 받을때
        bookService.saveOne(new Book(id, name, author)); // 입력받은 id,name,author를 book 객체로 만들어서 메소드실행

        return "redirect:/books";
        // POST 요청을 받았을때, 위의 요소들 저장 후 GET /books 연결
        // redirect 작업 성공시 3xx
    }

}
