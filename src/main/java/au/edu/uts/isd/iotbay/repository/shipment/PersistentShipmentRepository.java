package au.edu.uts.isd.iotbay.repository.shipment;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.repository.address.PersistentAddressRepository;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class PersistentShipmentRepository implements ShipmentRepository {

    private final ConnectionProvider datasource;

    public PersistentShipmentRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    private static final ResultExtractor<Address> ADDRESS_EXTRACTOR = PersistentAddressRepository.extractor("address_id", null);

    //TODO(harrison): implement extractor
    private static final ResultExtractor<Shipment> EXTRACTOR = r -> {
        int id = r.getInt("id");
        Order order = null; //TODO: get order
        Address address = ADDRESS_EXTRACTOR.extract(r);
        String method = r.getString("method");
        Date date = r.getDate("date");
        return new Shipment(id, order, address, method, date);
    };

    @Override
    @SneakyThrows
    public Optional<Shipment> findById(int id) {
        final String query = "SELECT * FROM shipment INNER JOIN address a ON a.id = address_id WHERE id = ? LIMIT 1;";
        final Shipment shipment = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, id);
            return EXTRACTOR.single(statement.executeQuery());
        });
        return Optional.ofNullable(shipment);
    }


    @Override
    @SneakyThrows
    public Collection<Shipment> all() {
        final String query = "SELECT * FROM shipment INNER JOIN address a ON a.id = address_id;";
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    @SneakyThrows
    public Shipment create(Shipment instance) {
        final String query = "INSERT INTO shipment (order_id, address_id, method, date) value (?, ?, ?, ?);";
        final Integer id = datasource.useKeyedPreparedStatement(query, statement -> {

            statement.setInt(1, instance.getOrder().getId());
            statement.setInt(2, instance.getAddress().getId());
            statement.setString(3, instance.getMethod());
            statement.setDate(4, instance.getDate());

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
    public Shipment update(Shipment instance) {
        final String query = "UPDATE shipment SET order_id = ?, address_id = ?, method = ?, date = ? WHERE id = ? LIMIT 1";
        final int updated = datasource.usePreparedStatement(query, statement -> {

            statement.setInt(1, instance.getOrder().getId());
            statement.setInt(2, instance.getAddress().getId());
            statement.setString(3, instance.getMethod());
            statement.setDate(4, instance.getDate());

            return statement.executeUpdate();
        });
        return updated <= 0 ? null : instance;
    }

    @Override
    @SneakyThrows
    public Shipment delete(Shipment instance) {
        final String query = "DELETE FROM shipment WHERE id = ?;";
        final int deleted = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, instance.getId());
            return statement.executeUpdate();
        });
        return deleted <= 0 ? null : instance;
    }
}
