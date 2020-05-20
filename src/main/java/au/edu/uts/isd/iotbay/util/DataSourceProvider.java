package au.edu.uts.isd.iotbay.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public final class DataSourceProvider {

    public static HikariDataSource from(String path) {
        HikariConfig config = new HikariConfig(path);
        return new HikariDataSource(config);
    }

    public static HikariDataSource from(Properties properties) {
        HikariConfig config = new HikariConfig(properties);
        return new HikariDataSource(config);
    }

    private DataSourceProvider() {
        throw new IllegalStateException("Unable to create an instance of the DataSourceProvider class");
    }
}
