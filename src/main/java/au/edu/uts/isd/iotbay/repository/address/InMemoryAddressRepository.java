package au.edu.uts.isd.iotbay.repository.address;

import au.edu.uts.isd.iotbay.model.address.Address;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAddressRepository implements AddressRepository {
    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    private final Map<Integer, Address> address;

    private InMemoryAddressRepository(Map<Integer, Address> address) {
        this.address = Objects.requireNonNull(address);
    }

    @Override
    public Collection<Address> all() {
        return new ArrayList<>(address.values());
    }

    @Override
    public Address create(Address instance) {
        final Integer id = SEQUENCE.getAndIncrement();
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
