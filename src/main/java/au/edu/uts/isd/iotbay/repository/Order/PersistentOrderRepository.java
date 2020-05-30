package au.edu.uts.isd.iotbay.repository.Order;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class PersistentOrderRepository implements OrderRepository {

    private final ConnectionProvider datasource;

    public PersistentOrderRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

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
    @SneakyThrows
    public Order create(Order instance) {
        final String query = "INSERT INTO order (id) values (?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {
            statement.setInt(1, instance.getId());

            final int inserted = statement.executeUpdate();

            if (inserted <= 0) {
                return null;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                return null;
            }
        });
        instance.setId(id);
        return id == null ? null : instance;
    }

    @Override
    @SneakyThrows
    public Order update(Order instance) {
        return null;
    }

    @Override
    @SneakyThrows
    public Order delete(Order instance) {
        final StringBuilder query = new StringBuilder("DELETE FROM order WHERE id = ?;");
        final int deleted = datasource.usePreparedStatement(query.toString(), statement -> {
            statement.setInt(1, instance.getId());
            return statement.executeUpdate();
        });
        return deleted <= 0 ? null : instance;
    }
}
