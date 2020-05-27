package au.edu.uts.isd.iotbay.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface ResultExtractor<T> {

    //TODO(harrison): documentation

    T extract(ResultSet results) throws SQLException;

    default T single(ResultSet results) throws SQLException {
        try (ResultSet rs = results) {
            if (rs.next()) {
                return extract(rs);
            }
            return null;
        }
    }

    default Collection<T> all(ResultSet results) throws SQLException {
        try (ResultSet rs = results) {
            final List<T> elements = new ArrayList<>(results.getFetchSize());
            while (rs.next()) {
                elements.add(extract(rs));
            }
            return elements;
        }
    }
}
