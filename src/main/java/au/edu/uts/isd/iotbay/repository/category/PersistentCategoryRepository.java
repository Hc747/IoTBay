package au.edu.uts.isd.iotbay.repository.category;

import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.persistence.jdbc.ConnectionProvider;
import au.edu.uts.isd.iotbay.persistence.jdbc.ResultExtractor;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class PersistentCategoryRepository implements CategoryRepository {

    private final ConnectionProvider datasource;

    public PersistentCategoryRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    private static final ResultExtractor<Category> EXTRACTOR = r -> {
        int id = r.getInt("id");
        String name = r.getString("name");
        String description = r.getString("description");
        boolean enabled = r.getBoolean("enabled");
        return new Category(id, name, description, enabled);
    };

    @Override
    @SneakyThrows
    public Optional<Category> findByCategoryId(int id) {
        final String query = "SELECT * FROM category WHERE id = ? LIMIT 1;";
        final Category category = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, id);
            return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(category);
    }

    @Override
    @SneakyThrows
    public Optional<Category> findByCategoryName(String name) {
        final String query = "SELECT * FROM category WHERE name = ? LIMIT 1;";
        final Category category = datasource.usePreparedStatement(query, statement -> {
            statement.setString(1, name);
            return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(category);
    }

    @Override
    @SneakyThrows
    public Collection<Category> all() {
        final String query = "SELECT * FROM category;";
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    @SneakyThrows
    public Category create(Category instance) {
        final String query = "INSERT INTO category (name, description, enabled) values (?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {

            statement.setString(1, instance.getName());
            statement.setString(2, instance.getDescription());
            statement.setBoolean(3, instance.isEnabled());

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
    public Category update(Category instance) {
        final String query = "UPDATE category SET name = ?, description = ?, enabled = ? WHERE id = ? LIMIT 1;";
        final int modified = datasource.usePreparedStatement(query, statement -> {

            statement.setString(1, instance.getName());
            statement.setString(2, instance.getDescription());
            statement.setBoolean(3, instance.isEnabled());
            statement.setInt(4, instance.getId());

            return statement.executeUpdate();
        });
        return modified <= 0 ? null : instance;
    }

    @Override
    @SneakyThrows
    public Category delete(Category instance) {
        final String query = "DELETE FROM category WHERE id = ?;";
        final int deleted = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, instance.getId());
            return statement.executeUpdate();
        });
        return deleted <= 0 ? null : instance;
    }
}
