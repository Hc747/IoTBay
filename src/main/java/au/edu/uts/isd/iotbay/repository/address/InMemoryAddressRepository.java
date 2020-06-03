package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.model.address.Address;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAddressRepository implements AddressRepository {

    private final Map<ObjectId, Address> address;

    private InMemoryAddressRepository(Map<ObjectId, Address> address) {
        this.address = Objects.requireNonNull(address);
    }

    @Override
    public Collection<Address> all() {
        return new ArrayList<>(address.values());
    }

    @Override
    public Address create(Address instance) {
        final ObjectId id = new ObjectId();
        return address.compute(id, (k, v) -> {
            instance.setId(id);
            return instance;
        });
    }

    @Override
    public Address update(Address instance) {
        address.put(instance.getId(), instance);
        return instance;
    }

    @Override
    public Address delete(Address instance) {
        return address.remove(instance.getId(), instance) ? instance : null;
    }

    public static InMemoryAddressRepository concurrent() {
        return new InMemoryAddressRepository(new ConcurrentHashMap<>());
    }

    public static InMemoryAddressRepository synchronised() {
        return new InMemoryAddressRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryAddressRepository unsynchronised() {
        return new InMemoryAddressRepository(new HashMap<>());
    }
}
