package tr.com.provera.pameraapi.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.com.provera.pameraapi.dto.NoteDto;
import tr.com.provera.pameraapi.exception.EntityNotFoundException;
import tr.com.provera.pameraapi.mapper.NoteMapper;
import tr.com.provera.pameraapi.model.Note;
import tr.com.provera.pameraapi.repository.NoteRepository;
import tr.com.provera.pameraapi.service.NoteService;
@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    @Override
    public Mono<NoteDto> createNote(NoteDto noteDto) {
        return Mono.just(noteDto)
                .map(noteMapper::toEntity)
                .flatMap(noteRepository::save)
                .map(noteMapper::toDto);
    }

    @Override
    public Mono<NoteDto> getNoteById(String id) {
        return noteRepository.findById(id)
                .map(noteMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Note not found")));
    }

    @Override
    public Flux<NoteDto> getAllNotes() {
        return noteRepository.findAll()
                .map(noteMapper::toDto);
    }
    @Override
    public Mono<NoteDto> updateNote(String id, NoteDto noteDto) {
        return noteRepository.findById(id)
                .flatMap(existingNote -> {
                    Note updatedNote = noteMapper.toEntity(noteDto);
                    updatedNote.setId(existingNote.getId()); // Ensure the correct ID is maintained
                    return noteRepository.save(updatedNote);
                })
                .map(noteMapper::toDto)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Note not found")));
    }


    @Override
    public Mono<Void> deleteNote(String id) {
        return noteRepository.deleteById(id);
    }
}
