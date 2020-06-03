package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProduct {

    private Product product;
    private int quantity;

}
