package au.edu.uts.isd.iotbay.model.user;

import au.edu.uts.isd.iotbay.model.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Identifiable {

    private ObjectId id;
    private String name, username, password, phone;
    private Role role;
    private boolean enabled;
    //TODO: implement timestamps
//    private Timestamp created, verified;

    public String[] names() {
        final String[] components = name.split(" +");
        final StringJoiner joiner = new StringJoiner(" ");
        for (int index = 1; index < components.length; index++) {
            joiner.add(components[index]);
        }
        return new String[] { components[0], joiner.length() == 0 ? null : joiner.toString() };
    }
}
