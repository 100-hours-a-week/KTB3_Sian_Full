package com.example.library.repository;

import com.example.library.dto.BookDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository {
    private final Map<Long, BookDto> store = new LinkedHashMap<>();
    private long sequence = 0;

    // 초기 데이터 추가
    public BookRepository() {
        if (store.isEmpty()) {
            save(new BookDto(null, "Clean Code", "Robert C. Martin",
                    "소프트웨어 장인 정신을 담은 책입니다.", "9780132350884"));
            save(new BookDto(null, "객체지향의 사실과 오해", "조영호",
                    "객체지향의 본질을 쉽게 설명합니다.", "9791186710770"));
            save(new BookDto(null, "Effective Java", "Joshua Bloch",
                    "자바 개발자를 위한 베스트 프랙티스 모음집입니다.", "9780134685991"));
        }
    }

    // 도서 등록 (id만)
    public BookDto save(BookDto book) {
        // 신규 도서
        if (book.getId() == null) {
            book.setId(++sequence);
        }
        store.put(book.getId(), book);
        return book;
    }

    public Optional<BookDto> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<BookDto> findAll() {
        return new ArrayList<>(store.values());
    }
}
