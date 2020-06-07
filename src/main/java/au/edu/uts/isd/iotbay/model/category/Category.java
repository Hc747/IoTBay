package au.edu.uts.isd.iotbay.model.category;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends IdentifiableModel {

    private String name;
    private String description;
    private boolean enabled;

    private List<Product> products;

    public static Category create(String name, String description, boolean enabled, Product... products) {
        return new Category(name, description, enabled, Arrays.asList(products));
    }

    public boolean addProducts(Product... products) {
        return this.products.addAll(Arrays.asList(products));
    }

    public boolean removeProducts(Product... products) {
        return this.products.removeAll(Arrays.asList(products));

    }

    //        if (products.length != 0) {
//            for (int i = 0; i < products.length - 1; i++){
//                this.products.add(products[i]);
//            }
//        }

    //        if (products.length != 0) {
//            for (int i = 0; i < products.length - 1; i++){
//                this.products.
//            }
//        }

//    static void a() {
//        Category first = create("Cars", "Cool Cars");
//        Category toys = create("Toys", "Even cooler toys", new Product(), new Product());
//    }
}
