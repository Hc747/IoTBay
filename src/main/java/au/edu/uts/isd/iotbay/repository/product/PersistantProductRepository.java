package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
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
    public Product create(Product instance) {
        return null;
    }

    @Override
    public Product update(Product instance) {
        return null;
    }

    @Override
    public Product delete(Product instance) {
        return null;
    }
}
