package au.edu.uts.isd.iotbay.model.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class UserLog {

    private Integer Id, userId;
    private String type;
    private Timestamp timestamp;
/*
private enum type4 {
    Login,
    Cancel,
    Logout,
    Update,
    Register;}*/
}
