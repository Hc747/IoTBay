package au.edu.uts.isd.iotbay.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    private Integer id;
    private String name;
    private String description;
    private Boolean enabled;
}
