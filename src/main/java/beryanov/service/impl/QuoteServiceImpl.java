package beryanov.service.impl;

import beryanov.dto.ChangeContentDto;
import beryanov.dto.CritiqueDto;
import beryanov.dto.ExtendedQuoteDto;
import beryanov.dto.QuoteDto;
import beryanov.exception.book.UnavailableBookException;
import beryanov.exception.critique.UnavailableCritiqueException;
import beryanov.exception.quote.UnavailableQuoteException;
import beryanov.mapper.QuoteMapper;
import beryanov.model.Book;
import beryanov.model.Critique;
import beryanov.model.Quote;
import beryanov.repository.BookRepository;
import beryanov.repository.QuoteRepository;
import beryanov.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<ExtendedQuoteDto> getAllQuotes() {
        List<Quote> quoteFoundList = quoteRepository.findAll();
        List<ExtendedQuoteDto> extendedQuoteFoundListDto = quoteMapper.toExtendedDtoList(quoteFoundList);

        log.info("Найдены цитаты: {}", extendedQuoteFoundListDto);

        return extendedQuoteFoundListDto;
    }

    @Override
    public QuoteDto editQuote(ChangeContentDto changeContentDto) {
        String quoteId = changeContentDto.getId();
        Optional<Quote> probableExistingQuote = quoteRepository.findById(quoteId);

        if (probableExistingQuote.isEmpty()) {
            throw new UnavailableQuoteException(quoteId);
        }

        Quote quoteToEdit = probableExistingQuote.get();
        quoteToEdit.setContent(changeContentDto.getContent());

        QuoteDto quoteEditedDto = quoteMapper.toDto(quoteRepository.save(quoteToEdit));

        log.info("Изменена цитата: {}", quoteEditedDto);

        return quoteEditedDto;
    }

    @Override
    public void removeQuote(String quoteId) {
        Optional<Quote> probableExistingQuote = quoteRepository.findById(quoteId);

        if (probableExistingQuote.isEmpty()) {
            throw new UnavailableQuoteException(quoteId);
        }

        Quote quoteToDelete = probableExistingQuote.get();
        quoteRepository.delete(quoteToDelete);

        log.info("Удалена цитата: {}", quoteMapper.toDto(quoteToDelete));
    }
}
