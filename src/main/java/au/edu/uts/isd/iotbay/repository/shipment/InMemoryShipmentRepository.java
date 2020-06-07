package au.edu.uts.isd.iotbay.repository.shipment;


import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;

public class InMemoryShipmentRepository extends InMemoryRepository<Shipment> implements ShipmentRepository {}
