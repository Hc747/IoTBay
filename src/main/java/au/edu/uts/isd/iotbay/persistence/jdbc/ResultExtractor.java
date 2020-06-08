package au.edu.uts.isd.iotbay.persistence.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The {@code ResultExtractor} interface provides a mechanism of uniformly transforming a {@code ResultSet} row into
 * a POJO of type {@code T}.
 * @param <T>
 *     The type of object to extract from the {@code ResultSet}
 */
@FunctionalInterface
public interface ResultExtractor<T> {

    /**
     * Provides a means of extracting a POJO of type {@code T} from a {@code ResultSet} row.
     */
    T extract(ResultSet results) throws SQLException;

    /**
     * Attempts to extract 1 row from the {@code ResultSet}
     */
    default T single(ResultSet results) throws SQLException {
        try (ResultSet rs = results) {
            if (rs.next()) {
                return extract(rs);
            }
            return null;
        }
    }

    /**
     * Attempts to extract all rows from the {@code ResultSet}
     */
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
