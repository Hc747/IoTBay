package au.edu.uts.isd.iotbay.persistence.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public final class ConnectionProviderFactory {

    public static ConnectionProvider hikari(String propertiesPath) {
        HikariDataSource datasource = new HikariDataSource(new HikariConfig(propertiesPath));
        return new HikariConnectionProvider(datasource);
    }

    public static ConnectionProvider hikari(Properties properties) {
        HikariDataSource datasource = new HikariDataSource(new HikariConfig(properties));
        return new HikariConnectionProvider(datasource);
    }

    private ConnectionProviderFactory() {
        throw new IllegalStateException("Unable to create an instance of: " + getClass().getSimpleName());
    }

    private static class HikariConnectionProvider implements ConnectionProvider {

        private final HikariDataSource datasource;

        private HikariConnectionProvider(HikariDataSource datasource) {
            this.datasource = Objects.requireNonNull(datasource);
        }

        @Override
        public Connection connection() throws SQLException {
            return datasource.getConnection();
        }

        @Override
        public void close() {
            datasource.close();
        }
    }
}
