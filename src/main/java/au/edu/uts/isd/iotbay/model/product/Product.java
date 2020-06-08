package au.edu.uts.isd.iotbay.model.product;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.persistence.Entity;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends IdentifiableModel {

    private String name;
    private String description;
    private int quantity;
    private double price;

    private List<ObjectId> categories;

    public static Product create(String name, String description, int quantity, double price, ObjectId... categories) {
        return new Product(name, description, quantity, price, Arrays.asList(categories));
    }

    public boolean addCategories(ObjectId... categories) {
        return this.categories.addAll(Arrays.asList(categories));
    }

    public boolean removeCategories(Category... categories) {
        return this.categories.removeAll(Arrays.asList(categories));

    }

    public String formatPrice(){
        return new DecimalFormat("###,##0.00").format(price);
    }

}
