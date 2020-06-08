package au.edu.uts.isd.iotbay.model.category;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends IdentifiableModel {
    private String name;
    private String description;

    public static Category create(String name, String description) {
        return new Category(name, description);
    }
}
