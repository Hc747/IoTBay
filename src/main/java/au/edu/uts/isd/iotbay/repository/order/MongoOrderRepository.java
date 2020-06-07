package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

import static com.mongodb.client.model.Filters.eq;

public class MongoOrderRepository extends MongoRepository<Order> implements OrderRepository {

    protected MongoOrderRepository(MongoDatabaseProvider datasource) {
        super(datasource, Order.class);
    }

}
