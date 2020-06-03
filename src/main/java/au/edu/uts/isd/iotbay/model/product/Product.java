package au.edu.uts.isd.iotbay.model.product;

import au.edu.uts.isd.iotbay.model.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class Product implements Identifiable {

    private ObjectId id;
    private String name;
    private String description;
    private int quantity;
    private double price;

}
