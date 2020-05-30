package au.edu.uts.isd.iotbay.repository.Order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.product.Product;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryOrderRepository implements OrderRepository {

    @Override
    public Optional<Order> findById(int id) {
        return Optional.empty();
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
    public OrderStatus addStatus(Order order, String status, String details) {
        return null;
    }

    @Override
    public Collection<Order> all() {
        return null;
    }

    @Override
    public Order create(Order instance) {
        return null;
    }

    @Override
    public Order update(Order instance) {
        return null;
    }

    @Override
    public Order delete(Order instance) {
        return null;
    }
}
