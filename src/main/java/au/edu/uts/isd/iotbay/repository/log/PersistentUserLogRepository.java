package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.Role;
import au.edu.uts.isd.iotbay.model.user.User;
import lombok.SneakyThrows;
import sun.rmi.runtime.Log;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class PersistentUserLogRepository implements UserLogRepository {

    private static final ResultExtractor<UserLog> EXTRACTOR = r -> {
        int id = r.getInt("id");
        int userId = r.getInt("userId");
        String type = r.getString("type");
        Timestamp timestamp = r.getTimestamp("timestamp");

        return new UserLog(id, userId, type, timestamp);
    };

    private final ConnectionProvider datasource;

    public PersistentUserLogRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }


    @SneakyThrows //TODO: consider implications
    public Optional<UserLog> findById(Integer userId) {
        final String query = "SELECT * FROM access_log WHERE user_id = ?;";
        final UserLog userlogs = datasource.usePreparedStatement(query, statement -> {
           statement.setInt(1, userId);
           return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(userlogs);
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public Collection<UserLog> all() {
        final String query = "SELECT * FROM access_log;";
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public UserLog create(UserLog instance) {
        final String query = "INSERT INTO access_log (id, user_Id, type, timestamp) values (?, ?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {

            statement.setString(1, null);
            statement.setInt(2, instance.getUserId());
            statement.setString(3, instance.getType());
            statement.setTimestamp(4, instance.getTimestamp());

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
    public UserLog update(UserLog instance) {
        return null;
    }

    @Override
    public UserLog delete(UserLog instance) {
        return null;
    }


    @SneakyThrows //TODO: consider implications
    public User update(User instance) {
        return null;
    }


    @SneakyThrows //TODO: consider implications
    public User delete(User instance) {

        return null;
    }

    @Override
    public Optional<UserLog> findByUserId(Integer userId) {
        return Optional.empty();
    }
}
