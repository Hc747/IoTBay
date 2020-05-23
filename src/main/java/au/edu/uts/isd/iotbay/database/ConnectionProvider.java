package au.edu.uts.isd.iotbay.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider extends AutoCloseable {

    Connection connection() throws SQLException;

}