package au.edu.uts.isd.iotbay.model.log;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLog extends IdentifiableModel {

    private User user;
    private String type;
    private LocalDate date;

}
