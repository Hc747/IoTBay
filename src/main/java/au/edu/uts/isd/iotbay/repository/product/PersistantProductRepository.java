package au.edu.uts.isd.iotbay.repository.product;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.product.Product;

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
    public Optional<Product> findByProductId(int product_id) {
        final String query = "SELECT * FROM products WHERE id = ? LIMIT 1;";
        try {
            final Product product = datasource.usePreparedStatement(query, statement -> {
                statement.setString(1, String.valueOf(product_id));
                return EXTRACTOR.single(statement.executeQuery());
            });
            return Optional.ofNullable(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //return Optional.ofNullable(new Product(product_id));
        }
        return Optional.ofNullable(new Product(-1));
    }

    @Override
    public Optional<Product> findByProductName(String product_name) {
        final String query = "SELECT * FROM products WHERE name = ? LIMIT 1;";
        try {
            final Product product = datasource.usePreparedStatement(query, statement -> {
                statement.setString(1, product_name);
                return EXTRACTOR.single(statement.executeQuery());
            });
            return Optional.ofNullable(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(new Product(-1));
    }

    @Override
    public Collection<Product> all() {
        final String query = "SELECT * FROM product;";
        try {
            return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    @Override
    public Collection<Product> findAll(Predicate<Product> criteria) {
        return null;
    }

    @Override
    public Optional<Product> find(Predicate<Product> criteria) {
        return Optional.empty();
    }
}
