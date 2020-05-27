package au.edu.uts.isd.iotbay.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface ConnectionProvider extends AutoCloseable {

    //TODO(harrison): documentation

    /**
     * Not recommended for direct use, as it requires direct resource management.
     */
    Connection connection() throws SQLException;

    default <T> T useConnection(SQLFunction<Connection, T> action) throws SQLException {
        try (Connection connection = connection()) {
            return action.apply(connection);
        }
    }

    default <T> T usePreparedStatement(String query, SQLFunction<PreparedStatement, T> action) throws SQLException {
        try (Connection connection = connection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                return action.apply(statement);
            }
        }
    }

    default <T> T useKeyedPreparedStatement(String query, SQLFunction<PreparedStatement, T> action) throws SQLException {
        try (Connection connection = connection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                return action.apply(statement);
            }
        }
    }

    default <T> T useStatement(SQLFunction<Statement, T> action) throws SQLException {
        try (Connection connection = connection()) {
            try (Statement statement = connection.createStatement()) {
                return action.apply(statement);
            }
        }
    }

    @FunctionalInterface
    interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }
}