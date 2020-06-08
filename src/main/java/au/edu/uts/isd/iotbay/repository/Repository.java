package au.edu.uts.isd.iotbay.repository;

import au.edu.uts.isd.iotbay.model.Identifiable;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A repository provides a collection of methods for interacting with some persistence mechanism.
 * The underlying implementation might be in-memory database, a relational database, a file, etc.
 *
 * @param <T>
 *     The type of element contained within this repository.
 */
public interface Repository<T extends Identifiable> {

    /**
     * Retrieves the first element from the underlying data store whose element equals the parameterised {@code id} ObjectId.
     *
     * @param id
     * The ObjectId to lookup.
     *
     * @return
     * An element whose id matches or null iff no elements matched the {@code id ObjectId}.
     */
    T findById(ObjectId id);

    /**
     * @see Repository#findById(ObjectId id)
     */
    default T findById(String id) {
        return findById(new ObjectId(id));
    }

    /**
     * Retrieves all elements from the underlying data store.
     *
     * @return
     * All elements held within the repository.
     */
    Collection<T> all();

    /**
     * Attempts to store the {@code instance} in the underlying data store.
     *
     * @param instance
     * The instance to persist (create).
     *
     * @return
     * - null iff the instance could not be persisted.
     * - the {@code instance} iff the instance could be persisted.
     */
    T create(T instance);

    /**
     * Attempts to update the {@code instance} in the underlying data store.
     *
     * @param instance
     * The instance to persist (update).
     *
     * @return
     * - null iff the instance could not be updated.
     * - the {@code instance} iff the instance could be updated.
     */
    T update(T instance);

    /**
     * Attempts to delete the {@code instance} from the underlying data store.
     *
     * @param instance
     * The instance to persist (delete).
     *
     * @return
     * - null iff the instance could not be deleted.
     * - the {@code instance} iff the instance could be deleted.
     */
    T delete(T instance);

    /**
     * Retrieves all elements from the underlying data store that match the parameterised {@code criteria} predicate.
     * @see Repository#all
     *
     * @param criteria
     * The predicate used to filter elements.
     *
     * @return
     * A collection containing all elements that much the {@code criteria} predicate.
     */
    default Collection<T> findAll(Predicate<T> criteria) {
        return all().stream().filter(criteria).collect(Collectors.toList());
    }

    /**
     * Retrieves the first element from the underlying data store that matches the parameterised {@code criteria} predicate.
     * @see Repository#all
     *
     * @param criteria
     * The predicate used to filter elements.
     *
     * @return
     * An {@code Optional} element containing the first matching element or null iff no elements matched the {@code criteria predicate}.
     */
    default Optional<T> find(Predicate<T> criteria) {
        return all().stream().filter(criteria).findAny();
    }
}