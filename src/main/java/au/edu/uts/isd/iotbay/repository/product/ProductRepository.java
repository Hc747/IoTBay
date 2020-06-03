package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface ProductRepository extends Repository<Product> {

    Optional<Product> findByProductId(ObjectId id);

    Optional<Product> findByProductName(String name);

    static ProductRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return InMemoryProductRepository.concurrent();
        }
        return null; //TODO: implement
//        return new PersistentProductRepository(datasource);
    }
}
