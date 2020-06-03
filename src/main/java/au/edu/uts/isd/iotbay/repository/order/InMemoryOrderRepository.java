package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;
import org.bson.types.ObjectId;

public class InMemoryOrderRepository extends InMemoryRepository<Order> implements OrderRepository {

    @Override
    public Order findById(ObjectId id) {
        return elements.get(id);
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
