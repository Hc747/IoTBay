package au.edu.uts.isd.iotbay.model.category;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends IdentifiableModel {

    private String name;
    private String description;
    private boolean enabled;

}
