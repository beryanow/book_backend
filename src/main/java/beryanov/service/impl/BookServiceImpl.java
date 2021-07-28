package beryanov.service.impl;

import beryanov.dto.BookDto;
import beryanov.exception.book.UnavailableBookException;
import beryanov.mapper.BookMapper;
import beryanov.model.Book;
import beryanov.model.State;
import beryanov.repository.BookRepository;
import beryanov.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto addBook(BookDto bookDto) {
        Optional<Book> probableExistingBook = bookRepository.findBookByNameAndAuthor(bookDto.getName(), bookDto.getAuthor());

        if (probableExistingBook.isPresent()) {
            log.info("Добавляемая книга уже существует: {}", probableExistingBook);

            return bookMapper.toDto(probableExistingBook.get());
        }

        Book bookToAdd = bookMapper.toEntity(bookDto);

        State readState = new State();
        readState.setBooksRead(List.of(bookToAdd));

        State readingState = new State();
        readingState.setBooksReading(List.of(bookToAdd));

        State toReadState = new State();
        toReadState.setBooksToRead(List.of(bookToAdd));

        State favouriteState = new State();
        favouriteState.setBooksFavourite(List.of(bookToAdd));

        bookToAdd.setRead(readState);
        bookToAdd.setReading(readingState);
        bookToAdd.setToRead(toReadState);
        bookToAdd.setFavourite(favouriteState);

        Book bookAdded = bookRepository.save(bookToAdd);
        BookDto bookAddedDto = bookMapper.toDto(bookAdded);

        log.info("Добавлена книга: {}", bookAddedDto);

        return bookAddedDto;
    }

    @Override
    public List<BookDto> addAllBooks(List<BookDto> bookDtoList) {
        List<BookDto> savedBooks = new ArrayList<>();

        for (BookDto bookDto : bookDtoList) {
            savedBooks.add(addBook(bookDto));
        }

        return savedBooks;
    }

    @Override
    public void removeBook(String bookId) {
        Optional<Book> probableExistingBook = bookRepository.findById(bookId);

        if (probableExistingBook.isEmpty()) {
            throw new UnavailableBookException(bookId);
        }

        Book bookToDelete = probableExistingBook.get();

        bookRepository.delete(bookToDelete);

        log.info("Удалена книга: {}", bookMapper.toDto(bookToDelete));
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> bookFoundList = bookRepository.findAll();
        List<BookDto> bookFoundListDto = bookMapper.toDtoList(bookFoundList);

        log.info("Найдены книги: {}", bookFoundListDto);

        return bookFoundListDto;
    }

    @Override
    public List<BookDto> getAllBooksToRead() {
        List<Book> bookToReadFoundList = bookRepository.findBooksToRead();
        List<BookDto> bookToReadFoundListDto = bookMapper.toDtoList(bookToReadFoundList);

        log.info("Найдены прочитанные книги: {}", bookToReadFoundListDto);

        return bookToReadFoundListDto;
    }
}
