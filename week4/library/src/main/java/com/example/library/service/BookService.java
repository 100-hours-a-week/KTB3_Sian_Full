package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // id 오름차순
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .sorted(Comparator.comparing(BookDto::getId))
                .toList();
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "해당 도서를 찾을 수 없습니다."
                ));
    }

}
