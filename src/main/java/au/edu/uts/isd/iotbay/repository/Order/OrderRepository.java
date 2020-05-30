package au.edu.uts.isd.iotbay.repository.Order;

import au.edu.uts.isd.iotbay.repository.Repository;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.database.ConnectionProvider;

import java.util.Optional;

public interface OrderRepository extends Repository<Order>{
    Optional<Order> findByUsername(String username);

    static OrderRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            //return in memory implementation
            return null;
        }
        //return persistent implementation
        return null;
    }
}
