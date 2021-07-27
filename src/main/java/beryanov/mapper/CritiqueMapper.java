package beryanov.mapper;

import beryanov.dto.CritiqueDto;
import beryanov.model.Critique;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CritiqueMapper {
    @Mapping(target = "bookId", source = "book.id")
    CritiqueDto toDto(Critique entity);
    Critique toEntity(CritiqueDto dto);

    List<CritiqueDto> toDtoList(List<Critique> entityList);
    List<Critique> toEntityList(List<CritiqueDto> dtoList);
}
