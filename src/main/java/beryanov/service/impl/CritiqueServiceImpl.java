package beryanov.service.impl;

import beryanov.dto.BookDto;
import beryanov.dto.ChangeContentDto;
import beryanov.dto.CritiqueDto;
import beryanov.exception.book.UnavailableBookException;
import beryanov.exception.critique.UnavailableCritiqueException;
import beryanov.exception.quote.UnavailableQuoteException;
import beryanov.mapper.BookMapper;
import beryanov.mapper.CritiqueMapper;
import beryanov.model.Book;
import beryanov.model.Critique;
import beryanov.model.Quote;
import beryanov.repository.BookRepository;
import beryanov.repository.CritiqueRepository;
import beryanov.service.CritiqueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CritiqueServiceImpl implements CritiqueService {
    private final BookRepository bookRepository;
    private final CritiqueRepository critiqueRepository;
    private final CritiqueMapper critiqueMapper;
    private final BookMapper bookMapper;

    @Override
    public CritiqueDto addCritique(CritiqueDto critiqueDto) {
        String bookId = critiqueDto.getBookId();
        Optional<Book> probableExistingBook = bookRepository.findById(bookId);

        if (probableExistingBook.isEmpty()) {
            throw new UnavailableBookException(bookId);
        }

        Book existingBook = probableExistingBook.get();
        BookDto existingBookDto = bookMapper.toDto(existingBook);

        if (existingBookDto.getCritique() != null) {
            log.info("Рецензия для книги: {} уже существует и будет перезаписана", existingBookDto);

            existingBook.getCritique().setContent(critiqueDto.getContent());

            Book bookAndCritiqueUpdated = bookRepository.save(existingBook);
            CritiqueDto critiqueUpdatedDto = critiqueMapper.toDto(bookAndCritiqueUpdated.getCritique());

            log.info("Обновлена рецензия: {}", critiqueUpdatedDto);

            return critiqueUpdatedDto;
        }

        Critique critiqueToAdd = new Critique();

        critiqueToAdd.setContent(critiqueDto.getContent());
        critiqueToAdd.setBook(existingBook);

        Critique critiqueAdded = critiqueRepository.save(critiqueToAdd);
        CritiqueDto critiqueAddedDto = critiqueMapper.toDto(critiqueAdded);

        log.info("Добавлена рецензия: {}", critiqueAddedDto);

        return critiqueAddedDto;
    }

    @Override
    public List<CritiqueDto> getAllCritiques() {
        List<Critique> critiqueFoundList = critiqueRepository.findAll();
        List<CritiqueDto> critiqueFoundListDto = critiqueMapper.toDtoList(critiqueFoundList);

        log.info("Найдены цитаты: {}", critiqueFoundListDto);

        return critiqueFoundListDto;
    }

    @Override
    public CritiqueDto editCritique(ChangeContentDto changeContentDto) {
        String critiqueId = changeContentDto.getId();
        Optional<Critique> probableExistingCritique = critiqueRepository.findById(critiqueId);

        if (probableExistingCritique.isEmpty()) {
            throw new UnavailableCritiqueException(critiqueId);
        }

        Critique critiqueToEdit = probableExistingCritique.get();
        critiqueToEdit.setContent(changeContentDto.getContent());

        CritiqueDto critiqueEditedDto = critiqueMapper.toDto(critiqueRepository.save(critiqueToEdit));

        log.info("Изменена рецензия: {}", critiqueEditedDto);

        return critiqueEditedDto;
    }

    @Override
    public void removeCritique(String critiqueId) {
        Optional<Critique> probableExistingCritique = critiqueRepository.findById(critiqueId);

        if (probableExistingCritique.isEmpty()) {
            throw new UnavailableCritiqueException(critiqueId);
        }

        Critique critiqueToDelete = probableExistingCritique.get();
        critiqueRepository.delete(critiqueToDelete);

        log.info("Удалена рецензия: {}", critiqueMapper.toDto(critiqueToDelete));
    }
}
