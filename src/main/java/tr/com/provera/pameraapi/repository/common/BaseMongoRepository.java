package tr.com.provera.pameraapi.repository.common;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.com.provera.pameraapi.exception.EntityNotFoundException;
import tr.com.provera.pameraapi.model.common.BaseDocument;

@NoRepositoryBean
public interface BaseMongoRepository<T extends BaseDocument, ID> extends ReactiveMongoRepository<T, ID> {

    // Custom method to find all documents that are not soft-deleted
    Flux<T> findAllByIsDeletedFalse();

    // Custom method to find a document by ID only if it's not soft-deleted
    Mono<T> findByIdAndIsDeletedFalse(ID id);

    // Override findById to handle soft-deleted entities as not found
    @Override
    default Mono<T> findById(ID id) {
        return findByIdAndIsDeletedFalse(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Entity not found or deleted with id: " + id)));
    }

    // Override findAll to return only non-deleted entities
    @Override
    default Flux<T> findAll() {
        return findAllByIsDeletedFalse();
    }

    // Override deleteById method for soft delete
    @Override
    default Mono<Void> deleteById(ID id) {
        return findByIdAndIsDeletedFalse(id)
                .flatMap(entity -> {
                    entity.setIsDeleted(true);
                    return save(entity).then();
                });
    }

    // Override delete method for soft delete
    @Override
    default Mono<Void> delete(T entity) {
        entity.setIsDeleted(true);
        return save(entity).then();
    }

    // Custom save method for handling versioning
    default Mono<T> saveWithVersioning(T entity) {
        if (!entity.isNew()) {
            entity.incrementVersion();
        }
        return save(entity);
    }
}
