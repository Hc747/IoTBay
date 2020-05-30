package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.repository.Repository;
import au.edu.uts.isd.iotbay.model.address.Address;

public interface AddressRepository extends Repository<Address> {

    static AddressRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            //return in memory implementation
            return null;
        }
        //return persistent implementation
        return null;
    }

}