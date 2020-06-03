package au.edu.uts.isd.iotbay.model.shipment;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipment extends IdentifiableModel {

    private Order order;
    private Address address;
    private String method;
    private Date date;

}
