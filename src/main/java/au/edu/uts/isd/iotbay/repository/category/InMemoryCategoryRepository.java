package au.edu.uts.isd.iotbay.repository.category;

import au.edu.uts.isd.iotbay.model.category.Category;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<ObjectId, Category> categories;

    public InMemoryCategoryRepository(Map<ObjectId, Category> categories) {
        this.categories = categories;
    }

    @Override
    public Optional<Category> findByCategoryId(int id) { return Optional.ofNullable(categories.get(id)); }

    @Override
    public Optional<Category> findByCategoryName(String name) {
        return find(category -> category.getName().equalsIgnoreCase(name));
    }

    @Override
    public Collection<Category> all() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Category create(Category instance) {
        final ObjectId id = new ObjectId();
        return categories.compute(id, (k, v) -> {
           instance.setId(id);
           return instance;
        });
    }

    @Override
    public Category update(Category instance) {
        categories.put(instance.getId(), instance);
        return instance;
    }

    @Override
    public Category delete(Category instance) { return categories.remove(instance.getId(),instance) ? instance : null; }

    public static InMemoryCategoryRepository concurrent() {
        return new InMemoryCategoryRepository(new ConcurrentHashMap<>());
    }

    public static InMemoryCategoryRepository synchronised() {
        return new InMemoryCategoryRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryCategoryRepository unsynchronised() {
        return new InMemoryCategoryRepository(new HashMap<>());
    }
}
