package au.edu.uts.isd.iotbay.repository.category;

import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;
import org.bson.types.ObjectId;

public class InMemoryCategoryRepository extends InMemoryRepository<Category> implements CategoryRepository {

    @Override
    public Category findById(ObjectId id) {
        return elements.get(id);
    }

    @Override
    public Category findByName(String name) {
        return find(category -> category.getName().equalsIgnoreCase(name)).orElse(null);
    }
}
