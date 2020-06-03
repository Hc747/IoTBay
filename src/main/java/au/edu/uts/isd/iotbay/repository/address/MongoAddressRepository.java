package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

public class MongoAddressRepository extends MongoRepository<Address> implements AddressRepository {

    protected MongoAddressRepository(MongoDatabaseProvider datasource) {
        super(datasource, Address.class);
    }
}
