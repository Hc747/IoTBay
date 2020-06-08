package au.edu.uts.isd.iotbay.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The {@code ConnectionProvider} interface provides a layer of abstraction over how the underlying {@code Connection} to
 * the database is produced. Furthermore, it also provides mechanisms for performing operations on the database
 * in a manner that takes care of resource management (closing connections, returning them to the connection pool, etc).
 */
public interface ConnectionProvider extends AutoCloseable {

    /**
     * Returns a connection from the underlying data-source; direct usage of this method is not recommended as
     * it requires the consumer to manage connection resources. Instead
     * @see ConnectionProvider#useConnection
     */
    Connection connection() throws SQLException;

    /**
     * Acquires a {@code Connection} to the database and executes the parameterised {@code action} upon the {@code Connection}.
     * Releases any resources acquired upon returning, regardless of failure.
     */
    default <T> T useConnection(SQLFunction<Connection, T> action) throws SQLException {
        try (Connection connection = connection()) {
            return action.apply(connection);
        }
    }

    /**
     * Acquires a {@code Connection} to the database, generates a {@code PreparedStatement} and executes
     * the parameterised {@code action} upon the {@code PreparedStatement}.
     * Releases any resources acquired upon returning, regardless of failure.
     */
    default <T> T usePreparedStatement(String query, SQLFunction<PreparedStatement, T> action) throws SQLException {
        try (Connection connection = connection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                return action.apply(statement);
            }
        }
    }

    /**
     * Acquires a {@code Connection} to the database, generates a {@code PreparedStatement} that returns generated keys
     * and executes the parameterised {@code action} upon the {@code PreparedStatement}.
     * Releases any resources acquired upon returning, regardless of failure.
     */
    default <T> T useKeyedPreparedStatement(String query, SQLFunction<PreparedStatement, T> action) throws SQLException {
        try (Connection connection = connection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                return action.apply(statement);
            }
        }
    }

    /**
     * Acquires a {@code Connection} to the database, generates a {@code Statement} and executes
     * the parameterised {@code action} upon the {@code Statement}.
     * Releases any resources acquired upon returning, regardless of failure.
     */
    default <T> T useStatement(SQLFunction<Statement, T> action) throws SQLException {
        try (Connection connection = connection()) {
            try (Statement statement = connection.createStatement()) {
                return action.apply(statement);
            }
        }
    }

    /**
     * @see java.util.function.Function<T,R>
     * Allows for a function to throw an SQLException
     */
    @FunctionalInterface
    interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }
}