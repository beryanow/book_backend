package beryanov.mapper;

import beryanov.dto.BookDto;
import beryanov.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CritiqueMapper.class, QuoteMapper.class, StateMapper.class})
public interface BookMapper {
    BookDto toDto(Book entity);
    Book toEntity(BookDto dto);

    List<BookDto> toDtoList(List<Book> entityList);
    List<Book> toEntityList(List<BookDto> dtoList);
}
