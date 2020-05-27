package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryProductRepository implements ProductRepository {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    private final Map<Integer, Product> products;

    public InMemoryProductRepository(Map<Integer, Product> products) {
        this.products = products;
    }

    @Override
    public Optional<Product> findByProductId(int product_id) {
        return Optional.ofNullable(products.get(product_id));
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
        final Integer id = SEQUENCE.getAndIncrement();
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
}
