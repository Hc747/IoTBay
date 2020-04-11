package au.edu.uts.isd.iotbay.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    
    private final UUID id;
    private String name, username, password;
    private Role role;
    
}
