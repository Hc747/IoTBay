package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;
import org.bson.types.ObjectId;

public class InMemoryProductRepository extends InMemoryRepository<Product> implements ProductRepository {

    @Override
    public Product findById(ObjectId id) {
        return elements.get(id);
    }

    @Override
    public Product findByName(String name) {
        return find(product -> product.getName().equalsIgnoreCase(name)).orElse(null);
    }
}
