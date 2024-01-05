package tr.com.provera.pameraapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.com.provera.pameraapi.dto.NoteDto;
import tr.com.provera.pameraapi.service.NoteService;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NoteDto> createNote(@RequestBody NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @GetMapping("/{id}")
    public Mono<NoteDto> getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    @GetMapping
    public Flux<NoteDto> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PutMapping("/{id}")
    public Mono<NoteDto> updateNote(@PathVariable String id, @RequestBody NoteDto noteDto) {
        return noteService.updateNote(id, noteDto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteNote(@PathVariable String id) {
        return noteService.deleteNote(id);
    }
}
