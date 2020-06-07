package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;
import org.bson.types.ObjectId;

public interface ProductRepository extends Repository<Product> {

    Product findById(ObjectId id);

    Product findByName(String name);

    static ProductRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return new InMemoryProductRepository();
        }
        return new MongoProductRepository(datasource);
    }
}
