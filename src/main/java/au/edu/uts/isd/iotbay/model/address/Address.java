package au.edu.uts.isd.iotbay.model.address;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private Integer id;
    private String address;
    private Integer postcode;

}
