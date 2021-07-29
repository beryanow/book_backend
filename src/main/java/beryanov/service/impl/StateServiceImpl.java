package beryanov.service.impl;

import beryanov.dto.StateDto;
import beryanov.enumeration.StateOption;
import beryanov.exception.book.UnavailableBookException;
import beryanov.exception.state.InvalidStateOptionException;
import beryanov.model.Book;
import beryanov.repository.BookRepository;
import beryanov.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {
    private final BookRepository bookRepository;

    @Override
    public void changeState(StateDto stateDto) {
        String bookId = stateDto.getBookId();
        Optional<Book> probableExistingBook = bookRepository.findById(bookId);

        if (probableExistingBook.isEmpty()) {
            throw new UnavailableBookException(bookId);
        }

        Book existingBook = probableExistingBook.get();
        StateOption stateOption = StateOption.valueOf(stateDto.getOption());

        boolean stateFlag = stateDto.isFlag();
        String rating = stateDto.getRating();

        switch (stateOption) {
            case READ -> {
                existingBook.getRead().setFlag(stateFlag);
                existingBook.getRead().setRating(rating);

                if (!"0".equals(rating)) {
                    log.info("Выставлена оценка: {} книге с id: {}", rating, bookId);
                }

                existingBook.getReading().setFlag(false);
                existingBook.getToRead().setFlag(false);
            }
            case READING -> {
                existingBook.getRead().setFlag(false);
                existingBook.getRead().setRating("0");

                existingBook.getReading().setFlag(stateFlag);
                existingBook.getToRead().setFlag(false);
            }
            case TO_READ -> {
                existingBook.getRead().setFlag(false);
                existingBook.getRead().setRating("0");

                existingBook.getReading().setFlag(false);
                existingBook.getToRead().setFlag(stateFlag);
            }
            case FAVOURITE -> existingBook.getFavourite().setFlag(stateFlag);
            default -> throw new InvalidStateOptionException(bookId);
        }

        bookRepository.save(existingBook);

        log.info("Произошло изменение состояния {} на '{}' книги с id: {}", stateOption, stateFlag, bookId);
    }
}
