package beryanov.mapper;

import beryanov.dto.ExtendedQuoteDto;
import beryanov.dto.QuoteDto;
import beryanov.model.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuoteMapper {
    @Mapping(target = "bookId", source = "book.id")
    QuoteDto toDto(Quote entity);

    @Mapping(target = "bookName", source = "book.name")
    @Mapping(target = "bookAuthor", source = "book.author")
    ExtendedQuoteDto toExtendedDto(Quote entity);

    Quote toEntity(QuoteDto dto);

    List<QuoteDto> toDtoList(List<Quote> entityList);
    List<ExtendedQuoteDto> toExtendedDtoList(List<Quote> entityList);
    List<Quote> toEntityList(List<QuoteDto> dtoList);
}