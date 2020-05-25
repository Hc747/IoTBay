package au.edu.uts.isd.iotbay.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Repository<T> {

    Collection<T> all();

    T save(T instance);

    T delete(T instance);

    default Collection<T> findAll(Predicate<T> criteria) {
        return all().stream().filter(criteria).collect(Collectors.toList());
    }

    default Optional<T> find(Predicate<T> criteria) {
        return all().stream().filter(criteria).findAny();
    }
}