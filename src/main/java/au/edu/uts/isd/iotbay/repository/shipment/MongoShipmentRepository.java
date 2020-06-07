package au.edu.uts.isd.iotbay.repository.shipment;

import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

public class MongoShipmentRepository extends MongoRepository<Shipment> implements ShipmentRepository {

    public MongoShipmentRepository(MongoDatabaseProvider datasource) {
        super(datasource, Shipment.class);
    }
}
