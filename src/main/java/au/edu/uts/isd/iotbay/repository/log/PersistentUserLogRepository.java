package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.user.PersistentUserRepository;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class PersistentUserLogRepository implements UserLogRepository {

    private static final ResultExtractor<User> USER_EXTRACTOR = PersistentUserRepository.extractor("user_id", null);

    private static final ResultExtractor<UserLog> EXTRACTOR = r -> {
        int id = r.getInt("id");
        User user = USER_EXTRACTOR.extract(r);
        String type = r.getString("type");
        Timestamp timestamp = r.getTimestamp("timestamp");
        return new UserLog(id, user, type, timestamp);
    };

    private final ConnectionProvider datasource;

    public PersistentUserLogRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    @SneakyThrows //TODO: consider implications
    public Collection<UserLog> findByUser(User user) {
        final String query = "SELECT * FROM access_log l INNER JOIN user u ON l.user_id = u.id WHERE user_id = ?;";
        return datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, user.getId());
            return EXTRACTOR.all(statement.executeQuery());
        });
    }

/*    @SneakyThrows //TODO: consider implications
    public Collection<UserLog> whereDateIs(User user, Date date) {
        final String query = "SELECT * FROM access_log l INNER JOIN user u ON l.user_id = u.id WHERE user_id = ?;";
        return datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, user.getId());
            statement.setInt(2, date);
            return EXTRACTOR.all(statement.executeQuery());
        });
    }*/

    @Override
    @SneakyThrows //TODO: consider implications
    public Collection<UserLog> all() {
        final String query = "SELECT * FROM access_log l INNER JOIN user u ON on l.user_id = u.id;";
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public UserLog create(UserLog instance) {
        final String query = "INSERT INTO access_log (id, user_Id, type, timestamp) values (?, ?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {

            statement.setString(1, null);
            statement.setInt(2, instance.getUser().getId());
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
}
