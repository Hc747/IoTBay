package au.edu.uts.isd.iotbay.repository.shipment;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.repository.Repository;

public interface ShipmentRepository extends Repository<Shipment> {

    static ShipmentRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            //return in memory implementation
            return null;
        }
        //return persistent implementation
        return null;
    }

}
