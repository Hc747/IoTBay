package au.edu.uts.isd.iotbay.model.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Harrison
 */
@Data
@AllArgsConstructor
public class User {
    
    private final UUID id;
    private String name, username, password;
    private Role role;
    
}
