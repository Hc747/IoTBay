package au.edu.uts.isd.iotbay.repository.shipment;

import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.persistence.jdbc.ConnectionProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Optional;

public interface ShipmentRepository extends Repository<Shipment> {

    Optional<Shipment> findById (int id);

    static ShipmentRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryShipmentRepository.concurrent();
        }
        return new PersistentShipmentRepository(datasource);
    }

}
