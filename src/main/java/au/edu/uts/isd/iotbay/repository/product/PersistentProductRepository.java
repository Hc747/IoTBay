package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.persistence.jdbc.ConnectionProvider;
import au.edu.uts.isd.iotbay.persistence.jdbc.ResultExtractor;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class PersistentProductRepository implements ProductRepository {

    private final ConnectionProvider datasource;

    public PersistentProductRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
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
        final String query = "SELECT * FROM product WHERE id = ? LIMIT 1;";
        final Product product = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, product_id);
            return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(product);
    }

    @Override
    @SneakyThrows
    public Optional<Product> findByProductName(String product_name) {
        final String query = "SELECT * FROM product WHERE name = ? LIMIT 1;";
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
        final String query = "INSERT INTO product (name, description, quantity, price) values (?, ?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {

            statement.setString(1, instance.getName());
            statement.setString(2, instance.getDescription());
            statement.setInt(3, instance.getQuantity());
            statement.setDouble(4, instance.getPrice());

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

            statement.setString(1, instance.getName());
            statement.setString(2, instance.getDescription());
            statement.setInt(3, instance.getQuantity());
            statement.setDouble(4, instance.getPrice());
            statement.setInt(5, instance.getId());

            return statement.executeUpdate();
        });
        return modified <= 0 ? null : instance;
    }

    @Override
    @SneakyThrows
    public Product delete(Product instance) {
        final String query = "DELETE FROM product WHERE id = ?;";
        final int deleted = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, instance.getId());
            return statement.executeUpdate();
        });
        return deleted <= 0 ? null : instance;
    }
}
