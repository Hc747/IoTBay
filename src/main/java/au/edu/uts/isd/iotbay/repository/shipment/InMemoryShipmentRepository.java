package au.edu.uts.isd.iotbay.repository.shipment;


import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryShipmentRepository implements ShipmentRepository {

    private final Map<ObjectId, Shipment> shipment;

    private InMemoryShipmentRepository(Map<ObjectId, Shipment> shipment) {
        this.shipment = Objects.requireNonNull(shipment);
    }

    @Override
    public Optional<Shipment> findById(int id) {
        return Optional.ofNullable(shipment.get(id));
    }

    @Override
    public Collection<Shipment> all() {
        return new ArrayList<>(shipment.values());
    }

    @Override
    public Shipment create(Shipment instance) {
        final ObjectId id = new ObjectId();
        return shipment.compute(id, (k, v) -> {
            instance.setId(id);
            return instance;
        });
    }

    @Override
    public Shipment update(Shipment instance) {
        shipment.put(instance.getId(), instance);
        return instance;
    }

    @Override
    public Shipment delete(Shipment instance) {
        return shipment.remove(instance.getId(), instance) ? instance : null;
    }

    public static InMemoryShipmentRepository concurrent() {
        return new InMemoryShipmentRepository(new ConcurrentHashMap<>());
    }

    public static InMemoryShipmentRepository synchronised() {
        return new InMemoryShipmentRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryShipmentRepository unsynchronised() {
        return new InMemoryShipmentRepository(new HashMap<>());
    }
}
