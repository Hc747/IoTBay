package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.Optional;
import java.sql.*;

public class PersistantProductRepository implements ProductRepository {
    private final ConnectionProvider datasource;

    public PersistantProductRepository(ConnectionProvider datasource) {
        this.datasource = datasource;
    }

    private static final ResultExtractor<Product> EXTRACTOR = r -> {
        int id = r.getInt("id");
        String name = r.getString("name");
        String description = r.getString("description");
        int quantity = r.getInt("quantity");
        double price = r.getDouble("price");
        return new Product(id, name, description, quantity, price);
    };

    @Override
    @SneakyThrows
    public Optional<Product> findByProductId(int product_id) {
        final String query = "SELECT * FROM products WHERE id = ? LIMIT 1;";
        final Product product = datasource.usePreparedStatement(query, statement -> {
            statement.setString(1, String.valueOf(product_id));
            return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(product);
    }

    @Override
    @SneakyThrows
    public Optional<Product> findByProductName(String product_name) {
        final String query = "SELECT * FROM products WHERE name = ? LIMIT 1;";
        final Product product = datasource.usePreparedStatement(query, statement -> {
            statement.setString(1, product_name);
            return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(product);
    }

    @Override
    @SneakyThrows
    public Collection<Product> all() {
        final String query = "SELECT * FROM product;";
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    @SneakyThrows
    public Product create(Product instance) {
        final String query = "INSERT INTO product (id, name, description, quantity, price) values (?, ?, ?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {

            statement.setInt(1, instance.getId());
            statement.setString(2, instance.getName());
            statement.setString(3, instance.getDescription());
            statement.setInt(4, instance.getQuantity());
            statement.setDouble(5, instance.getPrice());

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
    public Product update(Product instance) {
        final String query = "UPDATE product SET name = ?, description = ?, quantity = ?, price = ? WHERE id = ? LIMIT 1;";
        final int modified = datasource.usePreparedStatement(query, statement -> {

            statement.setInt(1, instance.getId());
            statement.setString(2, instance.getName());
            statement.setString(3, instance.getDescription());
            statement.setInt(4, instance.getQuantity());
            statement.setDouble(5, instance.getPrice());

            return statement.executeUpdate();
        });
        return modified <= 0 ? null : instance;
    }

    @Override
    @SneakyThrows
    public Product delete(Product instance) {
        final StringBuilder query = new StringBuilder("DELETE FROM user WHERE ");
        if (instance.getId() != null) {
            query.append("id = ?;");
        } else {
            return null;
        }
        final int deleted = datasource.usePreparedStatement(query.toString(), statement -> {
            if (instance.getId() != null) {
                statement.setInt(1, instance.getId());
            }
            return statement.executeUpdate();
        });
        return deleted <= 0 ? null : instance;
    }
}
