package au.edu.uts.isd.iotbay.model.shipment;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.address.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipment extends IdentifiableModel {

    private String name;
    private Address address;
    private String method;
    private LocalDate date;

}
