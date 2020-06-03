package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;

public class InMemoryAddressRepository extends InMemoryRepository<Address> implements AddressRepository {}
