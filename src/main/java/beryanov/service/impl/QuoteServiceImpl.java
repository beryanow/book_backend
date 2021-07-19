package beryanov.service.impl;

import beryanov.dto.QuoteDto;
import beryanov.exception.book.UnavailableBookException;
import beryanov.mapper.QuoteMapper;
import beryanov.model.Book;
import beryanov.model.Quote;
import beryanov.repository.BookRepository;
import beryanov.repository.QuoteRepository;
import beryanov.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final BookRepository bookRepository;
    private final QuoteMapper quoteMapper;

    @Override
    public QuoteDto addQuote(QuoteDto quoteDto) {
        String bookId = quoteDto.getBookId();
        Optional<Book> probableExistingBook = bookRepository.findById(bookId);

        if (probableExistingBook.isEmpty()) {
            throw new UnavailableBookException(bookId);
        }

        Quote quoteToAdd = new Quote();

        quoteToAdd.setContent(quoteDto.getContent());
        quoteToAdd.setBook(probableExistingBook.get());

        Quote quoteAdded = quoteRepository.save(quoteToAdd);
        QuoteDto quoteAddedDto = quoteMapper.toDto(quoteAdded);

        log.info("Добавлена цитата: {}", quoteAddedDto);

        return quoteAddedDto;
    }
}
