package tr.com.provera.pameraapi.repository;

import org.springframework.stereotype.Repository;
import tr.com.provera.pameraapi.model.Note;
import tr.com.provera.pameraapi.repository.common.BaseMongoRepository;

@Repository
public interface NoteRepository extends BaseMongoRepository<Note, String> {
    // Custom reactive query methods can be defined here if needed
}