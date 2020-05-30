package au.edu.uts.isd.iotbay.model.log;

import au.edu.uts.isd.iotbay.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class UserLog {

    private Integer id;
    private User user;
    private String type;
    private Timestamp timestamp;

}
