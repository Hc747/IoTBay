package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    private Product product;
    private int quantity;

}
