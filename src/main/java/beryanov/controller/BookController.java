package beryanov.controller;

import beryanov.dto.BookDto;
import beryanov.dto.ObjectIdHolder;
import beryanov.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/book")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "/add-all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> addAllBooks(@Valid @RequestBody List<BookDto> bookDtoList) {
        return bookService.addAllBooks(bookDtoList);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all-read", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllBooksRead() {
        return bookService.getAllBooksRead();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all-reading", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllBooksReading() {
        return bookService.getAllBooksReading();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all-to-read", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllBooksToRead() {
        return bookService.getAllBooksToRead();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all-favourite", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllBooksFavourite() {
        return bookService.getAllBooksFavourite();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeBook(@Valid @RequestBody ObjectIdHolder objectIdHolder) {
        bookService.removeBook(objectIdHolder.getId());
    }
}
