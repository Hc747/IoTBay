package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.user.Role;
import au.edu.uts.isd.iotbay.model.user.User;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class PersistentUserRepository implements UserRepository {

    private static final ResultExtractor<User> EXTRACTOR = r -> {
        int id = r.getInt("id");
        String name = r.getString("first_name") + (r.getString("last_name") == null ? "" : " " + r.getString("last_name"));
        String username = r.getString("email_address");
        String password = r.getString("password_hash");
        Role role = Role.findByOrdinal(r.getInt("role_id"));
        boolean enabled = r.getBoolean("enabled");
        Timestamp created = r.getTimestamp("created_at");
        Timestamp verified = r.getTimestamp("verified_at");
        return new User(id, name, username, password, role, enabled, created, verified);
    };

    private final ConnectionProvider datasource;

    public PersistentUserRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public Optional<User> findByUsername(String username) {
        final String query = "SELECT * FROM user WHERE email_address = ? LIMIT 1;";
        final User user = datasource.withPreparedStatement(query, statement -> {
           statement.setString(1, username);
           return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(user);
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public Collection<User> all() {
        final String query = "SELECT * FROM user;";
        return datasource.withStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public User save(User instance) {
        final boolean update = instance.getId() != null;
        final String query;

        if (update) {
            query = "UPDATE user SET email_address = ?, role_id = ?, first_name = ?, last_name = ?, password_hash = ?, enabled = ?, created_at = ?, verified_at = ? WHERE id = ? LIMIT 1;";
        } else {
            query = "INSERT INTO user (email_address, role_id, first_name, last_name, password_hash, enabled, created_at, verified_at) values (?, ?, ?, ?, ?, ?, ?, ?);";
        }

        final int modified = datasource.withPreparedStatement(query, statement -> {
            final String[] names = instance.getNameComponents();

            statement.setString(1, instance.getUsername());
            statement.setInt(2, instance.getRole().ordinal());
            statement.setString(3, names[0]);
            statement.setString(4, names[1]);
            statement.setString(5, instance.getPassword());
            statement.setBoolean(6, instance.isEnabled());
            statement.setTimestamp(7, instance.getCreated());
            statement.setTimestamp(8, instance.getVerified());

            if (update) {
                statement.setInt(6, instance.getId());
            }

            return statement.executeUpdate();
        });
        return modified > 0 ? findByUsername(instance.getUsername()).orElseThrow(SQLException::new) : null;
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public User delete(User instance) {
        final StringBuilder query = new StringBuilder("DELETE FROM user WHERE ");
        if (instance.getId() != null) {
            query.append("id = ?;");
        } else if (instance.getUsername() != null) {
            query.append("email_address = ?;");
        } else {
            return null;
        }
        final int deleted = datasource.withPreparedStatement(query.toString(), statement -> {
            if (instance.getId() != null) {
                statement.setInt(1, instance.getId());
            } else {
                statement.setString(1, instance.getUsername());
            }
            return statement.executeUpdate();
        });
        return deleted > 0 ? instance : null;
    }
}
