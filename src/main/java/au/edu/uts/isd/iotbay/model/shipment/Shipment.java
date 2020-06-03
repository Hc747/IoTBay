package au.edu.uts.isd.iotbay.model.shipment;

import au.edu.uts.isd.iotbay.model.Identifiable;
import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Shipment implements Identifiable {

    private ObjectId id;
    private Order order;
    private Address address;
    private String method;
    private Date date;

}
