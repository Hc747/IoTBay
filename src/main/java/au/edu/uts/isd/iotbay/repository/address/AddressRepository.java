package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;


public interface AddressRepository extends Repository<Address> {

    static AddressRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return InMemoryAddressRepository.concurrent();
        }
        return null; //TODO: implement
//        return new PersistentAddressRepository(datasource);
    }
}