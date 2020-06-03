package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<ObjectId, Product> products;

    public InMemoryProductRepository(Map<ObjectId, Product> products) {
        this.products = products;
    }

    @Override
    public Optional<Product> findByProductId(ObjectId id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Optional<Product> findByProductName(String product_name) {
        return find(product -> product.getName().equalsIgnoreCase(product_name));
    }

    @Override
    public Collection<Product> all() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product create(Product instance) {
        final ObjectId id = new ObjectId();
        return products.compute(id, (k, v) -> {
            instance.setId(id);
            return instance;
        });
    }

    @Override
    public Product update(Product instance) {
        products.put(instance.getId(), instance);
        return instance;
    }

    @Override
    public Product delete(Product instance) {
        return products.remove(instance.getId(), instance) ? instance : null;
    }

    public static InMemoryProductRepository concurrent() {
        return new InMemoryProductRepository(new ConcurrentHashMap<>());
    }

    public static InMemoryProductRepository synchronised() {
        return new InMemoryProductRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryProductRepository unsynchronised() {
        return new InMemoryProductRepository(new HashMap<>());
    }
}
