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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

        switch (bookDto.getStateOption()) {
            case FAVOURITE -> {
                favouriteState.setFlag(true);
                log.info("Добавляемая книга будет помечена избранной");
            }
            case READ -> {
                readState.setFlag(true);
                readState.setRating("0");
                log.info("Добавляемая книга будет помечена прочитанной");
            }
            case READING -> {
                readingState.setFlag(true);
                log.info("Добавляемая книга будет помечена текущей");
            }
            case TO_READ -> {
                toReadState.setFlag(true);
                log.info("Добавляемая книга будет помечена желанной");
            }
        }

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
    public List<BookDto> getAllBooksQuoted() {
        List<Book> bookFoundList = bookRepository.findAll();
        List<Book> bookFoundListQuoted = bookFoundList.stream()
                .filter(book -> book.getQuotes() != null && book.getQuotes().size() != 0)
                .collect(Collectors.toList());
        List<BookDto> bookFoundListQuotedDto = bookMapper.toDtoList(bookFoundListQuoted);

        log.info("Найдены книги с цитатами: {}", bookFoundListQuotedDto);

        return bookFoundListQuotedDto;
    }

    @Override
    public List<BookDto> getAllBooksCritiqued() {
        List<Book> bookFoundList = bookRepository.findAll();
        List<Book> bookFoundListCritiqued = bookFoundList.stream()
                .filter(book -> book.getCritique() != null && book.getCritique().getContent() != null && book.getCritique().getContent().length() != 0)
                .collect(Collectors.toList());
        List<BookDto> bookFoundListCritiquedDto = bookMapper.toDtoList(bookFoundListCritiqued);

        log.info("Найдены книги с резенциями: {}", bookFoundListCritiquedDto);

        return bookFoundListCritiquedDto;
    }

    @Override
    public Map<String, List<BookDto>> getAllAuthorGroupedBooks() {
        List<Book> bookFoundList = bookRepository.findAll();
        Map<String, List<BookDto>> bookListAuthorGrouped = bookFoundList.stream()
                .collect(Collectors.groupingBy(Book::getAuthor, Collectors.mapping(bookMapper::toDto, Collectors.toList())));

        log.info("Найдены сгруппированные по авторам книги: {}", bookListAuthorGrouped);

        return bookListAuthorGrouped;
    }

    @Override
    public List<BookDto> getAllBooksRead() {
        List<Book> bookReadFoundList = bookRepository.findBooksRead();
        List<BookDto> bookReadFoundListDto = bookMapper.toDtoList(bookReadFoundList);

        log.info("Найдены прочитанные книги: {}", bookReadFoundListDto);

        return bookReadFoundListDto;
    }

    @Override
    public List<BookDto> getAllBooksReading() {
        List<Book> bookReadingFoundList = bookRepository.findBooksReading();
        List<BookDto> bookReadingFoundListDto = bookMapper.toDtoList(bookReadingFoundList);

        log.info("Найдены текущие книги: {}", bookReadingFoundListDto);

        return bookReadingFoundListDto;
    }

    @Override
    public List<BookDto> getAllBooksToRead() {
        List<Book> bookToReadFoundList = bookRepository.findBooksToRead();
        List<BookDto> bookToReadFoundListDto = bookMapper.toDtoList(bookToReadFoundList);

        log.info("Найдены желанные книги: {}", bookToReadFoundListDto);

        return bookToReadFoundListDto;
    }

    @Override
    public List<BookDto> getAllBooksFavourite() {
        List<Book> bookFavouriteFoundList = bookRepository.findBooksFavourite();
        List<BookDto> bookFavouriteFoundListDto = bookMapper.toDtoList(bookFavouriteFoundList);

        log.info("Найдены избранные книги: {}", bookFavouriteFoundListDto);

        return bookFavouriteFoundListDto;
    }
}
