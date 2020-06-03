package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.persistence.jdbc.ConnectionProvider;
import au.edu.uts.isd.iotbay.repository.Repository;


public interface AddressRepository extends Repository<Address> {

    static AddressRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryAddressRepository.concurrent();
        }
        return new PersistentAddressRepository(datasource);
    }
}