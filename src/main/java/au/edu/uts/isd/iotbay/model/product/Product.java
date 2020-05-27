package au.edu.uts.isd.iotbay.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private Integer id;
    private String name;
    private String description;
    private int quantity;
    private double price;

}
