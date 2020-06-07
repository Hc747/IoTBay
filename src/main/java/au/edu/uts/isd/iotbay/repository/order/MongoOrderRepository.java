package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

public class MongoOrderRepository extends MongoRepository<Order> implements OrderRepository {

    protected MongoOrderRepository(MongoDatabaseProvider datasource) {
        super(datasource, Order.class);
    }
}
