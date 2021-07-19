package beryanov.service;

import beryanov.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    List<BookDto> getAllBooks();
}
