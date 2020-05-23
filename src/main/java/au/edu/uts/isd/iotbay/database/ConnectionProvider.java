package au.edu.uts.isd.iotbay.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface ConnectionProvider extends AutoCloseable {

    /**
     * Not recommended for direct use, as it requires direct resource management.
     */
    Connection connection() throws SQLException;

    default <T> T withConnection(SQLFunction<Connection, T> action) throws SQLException {
        try (Connection connection = connection()) {
            return action.apply(connection);
        }
    }

    default <T> T withStatement(SQLFunction<Statement, T> action) throws SQLException {
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