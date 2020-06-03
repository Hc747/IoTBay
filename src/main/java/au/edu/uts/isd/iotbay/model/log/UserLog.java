package au.edu.uts.isd.iotbay.model.log;

import au.edu.uts.isd.iotbay.model.Identifiable;
import au.edu.uts.isd.iotbay.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class UserLog implements Identifiable {

    private ObjectId id;
    private User user;
    private String type;
    private Timestamp timestamp;

}
