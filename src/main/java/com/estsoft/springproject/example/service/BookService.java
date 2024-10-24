package com.estsoft.springproject.example.service;

import com.estsoft.springproject.example.entity.Book;
import com.estsoft.springproject.example.entity.BookDTO;
import com.estsoft.springproject.example.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BookService {
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll(Sort.by("id")); // default -> 오름차순
        // select * from book order by id;
    }

    public Book findBy(String id) {
        return bookRepository.findById(id).orElse(new Book());
        // .orElse(new Book())  -> default 값으로 빈 객체
    }


    public Book saveOne(Book book) {
        return bookRepository.save(book);
    }

}
