package au.edu.uts.isd.iotbay.repository;

import au.edu.uts.isd.iotbay.model.Identifiable;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InMemoryRepository<T extends Identifiable> implements Repository<T> {

    protected final Map<ObjectId, T> elements;

    protected InMemoryRepository(Map<ObjectId, T> elements) {
        this.elements = Objects.requireNonNull(elements);
    }

    protected InMemoryRepository() {
        this(new ConcurrentHashMap<>());
    }

    @Override
    public Collection<T> all() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public T create(T instance) {
        if (instance.getId() == null) {
            instance.setId(new ObjectId());
            elements.put(instance.getId(), instance);
            return instance;
        }
        return update(instance);
    }

    @Override
    public T update(T instance) {
        return elements.compute(instance.getId(), (k, v) -> instance);
    }

    @Override
    public T delete(T instance) {
        return elements.remove(instance.getId());
    }
}
