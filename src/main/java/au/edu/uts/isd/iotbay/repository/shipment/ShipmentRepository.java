package au.edu.uts.isd.iotbay.repository.shipment;

import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

public interface ShipmentRepository extends Repository<Shipment> {

    static ShipmentRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return new InMemoryShipmentRepository();
        }
        return new MongoShipmentRepository(datasource);
    }
}
