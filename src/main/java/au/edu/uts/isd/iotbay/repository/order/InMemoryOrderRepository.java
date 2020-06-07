package au.edu.uts.isd.iotbay.repository.order;

import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;

public class InMemoryOrderRepository extends InMemoryRepository<Order> implements OrderRepository {}
