package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;
import org.bson.types.ObjectId;

public interface OrderRepository extends Repository<Order> {

    //TODO: refactor
    Order findById(ObjectId id);

    OrderProduct addProduct(Order order, Product product, int quantity);

    OrderProduct removeProduct(Order order, Product product, int quantity);

    OrderStatus status(Order order, String status, String details);

    static OrderRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return new InMemoryOrderRepository();
        }
        return new MongoOrderRepository(datasource);
    }
}
