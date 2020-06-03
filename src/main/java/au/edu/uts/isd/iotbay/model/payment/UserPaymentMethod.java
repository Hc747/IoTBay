package au.edu.uts.isd.iotbay.model.payment;

import au.edu.uts.isd.iotbay.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class UserPaymentMethod {

    private ObjectId id;
    private User user;
    private PaymentMethod method;

}
