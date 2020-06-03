package au.edu.uts.isd.iotbay.model.address;

import au.edu.uts.isd.iotbay.model.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class Address implements Identifiable {

    private ObjectId id;
    private String address;
    private Integer postcode;

}
