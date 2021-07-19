package beryanov.controller;

import beryanov.dto.BookDto;
import beryanov.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public BookDto add(@Valid @RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAll() {
        return bookService.getAllBooks();
    }
}
