package au.edu.uts.isd.iotbay.repository.Order;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.repository.payment.PersistentPaymentMethodRepository;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class PersistentOrderRepository implements OrderRepository {

    private static final ResultExtractor<PaymentMethod> PAYMENT_METHOD_EXTRACTOR = PersistentPaymentMethodRepository.extractor("payment_method_id", null);

    private static final ResultExtractor<Order> EXTRACTOR = r -> {
        int id = r.getInt("id");
        PaymentMethod payment = PAYMENT_METHOD_EXTRACTOR.extract(r);
        return new Order(id, payment);
    };

    private final ConnectionProvider datasource;

    public PersistentOrderRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    @Override
    @SneakyThrows
    public Optional<Order> findById(int id) {
        final String query = "SELECT * FROM order WHERE id = ? LIMIT 1;";
        final Order order = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, id);
            return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(order);
    }

    @Override
    @SneakyThrows
    public OrderProduct addProduct(Order order, Product product, int quantity) {
        final String query = "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {
            statement.setInt(1, order.getId());
            statement.setInt(2, product.getId());
            statement.setInt(3, quantity);

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

        if (id == null) {
            return null;
        }
        return new OrderProduct(id, order, product, quantity);
    }

    @Override
    @SneakyThrows
    public OrderProduct removeProduct(Order order, Product product, int quantity) {
        return null;
    }

    @Override
    @SneakyThrows
    public OrderStatus addStatus(Order order, String status, String details) {
        final String query = "INSERT INTO order_status (order_id, status, details, timestamp) VALUES (?, ?, ?, ?);";
        final Timestamp timestamp = Timestamp.from(Instant.now());
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {
            statement.setInt(1, order.getId());
            statement.setString(2, status);
            statement.setString(3, details);
            statement.setTimestamp(4, timestamp);

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

        if (id == null) {
            return null;
        }

        return new OrderStatus(id, order, status, details, timestamp);
    }

    @Override
    @SneakyThrows
    public Collection<Order> all() {
        final String query = "SELECT * FROM order;";
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
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
