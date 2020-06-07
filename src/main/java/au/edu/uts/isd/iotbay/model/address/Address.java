package au.edu.uts.isd.iotbay.model.address;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address extends IdentifiableModel {

    private String address;
    private String postcode;
}
