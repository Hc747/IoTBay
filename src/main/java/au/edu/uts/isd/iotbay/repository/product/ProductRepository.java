package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Optional;

public interface ProductRepository extends Repository<Product> {
    Optional<Product> findByProductId(int product_id);

    Optional<Product> findByProductName(String product_name);

    static ProductRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryProductRepository.concurrent();
        }
        return new PersistentProductRepository(datasource);
    }
}
