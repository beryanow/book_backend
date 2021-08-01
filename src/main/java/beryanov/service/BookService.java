package beryanov.service;

import beryanov.dto.BookDto;

import java.util.List;
import java.util.Map;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    List<BookDto> addAllBooks(List<BookDto> bookDtoList);
    void removeBook(String bookId);
    List<BookDto> getAllBooks();
    List<BookDto> getAllBooksQuoted();
    List<BookDto> getAllBooksCritiqued();
    Map<String, List<BookDto>> getAllAuthorGroupedBooks();
    List<BookDto> getAllBooksRead();
    List<BookDto> getAllBooksReading();
    List<BookDto> getAllBooksToRead();
    List<BookDto> getAllBooksFavourite();
}
