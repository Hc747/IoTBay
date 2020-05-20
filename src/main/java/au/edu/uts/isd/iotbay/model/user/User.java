package au.edu.uts.isd.iotbay.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    
    private final int id;
    private String name, username, password;
    private Role role;
    
}
