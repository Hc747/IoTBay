package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class OrderProduct {

    private ObjectId id;
    private Order order;
    private Product product;
    private int quantity;

}
