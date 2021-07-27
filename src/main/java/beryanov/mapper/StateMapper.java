package beryanov.mapper;

import beryanov.dto.StateDto;
import beryanov.model.State;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StateMapper {
//    @Mapping(target = "bookId", source = "book.id")
    StateDto toDto(State entity);
    State toEntity(StateDto dto);

    List<StateDto> toDtoList(List<State> entityList);
    List<State> toEntityList(List<StateDto> dtoList);
}
