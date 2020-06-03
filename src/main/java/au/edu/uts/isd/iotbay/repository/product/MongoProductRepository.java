package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

import static com.mongodb.client.model.Filters.eq;

public class MongoProductRepository extends MongoRepository<Product> implements ProductRepository {

    protected MongoProductRepository(MongoDatabaseProvider datasource) {
        super(datasource, Product.class);
    }

    @Override
    public Product findByName(String name) {
        return find(eq("name", name));
    }
}
