package au.edu.uts.isd.iotbay.model.shipment;

import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.model.order.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Shipment {

    private Integer id;
    private Order order;
    private Address address;
    private String method;
    private Date date;

}
