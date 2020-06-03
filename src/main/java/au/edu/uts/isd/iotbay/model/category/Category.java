package au.edu.uts.isd.iotbay.model.category;

import au.edu.uts.isd.iotbay.model.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class Category implements Identifiable {

    private ObjectId id;
    private String name;
    private String description;
    private boolean enabled;
}
