package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.service.BookService;
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

    @GetMapping
    public String listBooks(Model model) {
        List<BookDto> books = bookService.getAllBooks();

        model.addAttribute("books", books);

        return "books/list";
    }

    @GetMapping("/{id}")
    public String detailBook(@PathVariable Long id, Model model) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "books/detail";
    }

    @GetMapping("/new")
    public String newBookForm(Model model) {
        model.addAttribute("bookDto", new BookDto());
        return "books/form";
    }

    @PostMapping
    public String createBook(@ModelAttribute BookDto bookDto) {
        bookService.createBook(bookDto);
        return "redirect:/books";
    }

}
