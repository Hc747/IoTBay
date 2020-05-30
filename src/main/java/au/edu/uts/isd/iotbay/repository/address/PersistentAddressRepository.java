package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.address.Address;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Objects;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

public class PersistentAddressRepository implements AddressRepository {

    private final ConnectionProvider datasource;

    public PersistentAddressRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    private static final ResultExtractor<Address> EXTRACTOR = r -> {

        Integer id = r.getInt("id");
        String address = r.getString("address");
        Integer postcode = r.getInt("postcode");

        return new Address(id, address, postcode);
    };



    @Override
    @SneakyThrows
    public Collection<Address> all() {
        final String query = "SELECT * FROM address;";
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    @SneakyThrows
    public Address create(Address instance) {
        final String query = "INSERT INTO address(id, address, postcode) value (?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {

           statement.setInt(1, instance.getId());
           statement.setString(2, instance.getAddress());
           statement.setInt(3, instance.getPostcode());

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
    @SneakyThrows
    public Address update(Address instance) {
        final String query = "UPDATE address SET address = ?, postcode = ? WHERE id = ? LIMIT 1";
        final int updated = datasource.usePreparedStatement(query, statement -> {

            statement.setInt(1, instance.getId());
            statement.setString(2, instance.getAddress());
            statement.setInt(3, instance.getPostcode());

            return statement.executeUpdate();
        });
        return updated <= 0 ? null : instance;
    }

    @Override
    @SneakyThrows
    public Address delete(Address instance) {
        final String query = "DELETE FROM address WHERE id = ?;";
        final int deleted = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, instance.getId());
            return statement.executeUpdate();
        });
        return deleted <= 0 ? null : instance;
    }
}
