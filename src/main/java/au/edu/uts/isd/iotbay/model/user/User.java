package au.edu.uts.isd.iotbay.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.StringJoiner;

@Data
@AllArgsConstructor
public class User {
    
    private Integer id;
    private String name, username, password;
    private Role role;

    public String[] getNames() {
        final String[] components = name.split(" ");
        if (components.length == 1) {
            return new String[] { components[0], null };
        }
        final StringJoiner joiner = new StringJoiner(" ");
        for (int index = 1; index < components.length; index++) {
            joiner.add(components[index]);
        }
        return new String[] { components[0], joiner.toString() };
    }
    
}
