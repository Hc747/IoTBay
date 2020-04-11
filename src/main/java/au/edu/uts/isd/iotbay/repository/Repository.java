package au.edu.uts.isd.iotbay.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface Repository<T> {

    Collection<T> all();

    Collection<T> findAll(Predicate<T> criteria);

    Optional<T> find(Predicate<T> criteria);

    T save(T instance);

    T delete(T instance);
}