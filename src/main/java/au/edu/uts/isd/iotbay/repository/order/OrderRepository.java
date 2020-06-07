package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

public interface OrderRepository extends Repository<Order> {

    static OrderRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return new InMemoryOrderRepository();
        }
        return new MongoOrderRepository(datasource);
    }
}
