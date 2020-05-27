package au.edu.uts.isd.iotbay.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String name, username, password;
    private Role role;
    private boolean enabled;
    private Timestamp created, verified;

    public String[] getNameComponents() {
        final String[] components = name.split(" +");
        final StringJoiner joiner = new StringJoiner(" ");
        for (int index = 1; index < components.length; index++) {
            joiner.add(components[index]);
        }
        return new String[] { components[0], joiner.length() == 0 ? null : joiner.toString() };
    }
}
