package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.jdbc.ConnectionProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Optional;

public interface OrderRepository extends Repository<Order> {

    Optional<Order> findById(int id);

    OrderProduct addProduct(Order order, Product product, int quantity);

    OrderProduct removeProduct(Order order, Product product, int quantity);

    OrderStatus status(Order order, String status, String details);

    static OrderRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
//            return InMemoryOrderRepository.concurrent();
        }
        return new PersistentOrderRepository(datasource);
    }
}
