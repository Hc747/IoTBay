package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.lang.String.valueOf;

public class InMemoryProductRepository implements ProductRepository {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    private final Map<String, Product> products;

    public InMemoryProductRepository(Map<String, Product> products) {
        this.products = products;
    }

    @Override
    public Optional<Product> findByProductId(int product_id) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByProductName(String product_name) {
        return Optional.empty();
    }

    @Override
    public Collection<Product> all() {
        return new ArrayList<>(products.values());
        //return null;
    }

    @Override
    public Product create(Product instance) {
        return products.compute(valueOf(instance.getProduct_id()), (String k, Product v) -> {
            instance.setProduct_id(SEQUENCE.get());
            return instance;
        });
        //return null;
    }

    @Override
    public Product update(Product instance) {
        products.put(String.valueOf(instance.getProduct_id()), instance);
        return instance;
        //return null;
    }

    @Override
    public Product delete(Product instance) {
        return products.remove(String.valueOf(instance.getProduct_id()), instance) ? instance : null;
    }

    @Override
    public Collection<Product> findAll(Predicate<Product> criteria) {
        return null;
    }

    @Override
    public Optional<Product> find(Predicate<Product> criteria) {
        return Optional.empty();
    }
}
