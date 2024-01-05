package tr.com.provera.pameraapi.mapper;

import org.mapstruct.Mapper;
import tr.com.provera.pameraapi.dto.NoteDto;
import tr.com.provera.pameraapi.model.Note;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteDto toDto(Note entity);
    Note toEntity(NoteDto dto);
}