package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

public class MongoOrderRepository extends MongoRepository<Order> implements OrderRepository {

    protected MongoOrderRepository(MongoDatabaseProvider datasource) {
        super(datasource, Order.class);
    }

    @Override
    public OrderProduct addProduct(Order order, Product product, int quantity) {
        return null;
    }

    @Override
    public OrderProduct removeProduct(Order order, Product product, int quantity) {
        return null;
    }

    @Override
    public OrderStatus status(Order order, String status, String details) {
        return null;
    }
}
