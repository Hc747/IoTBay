package au.edu.uts.isd.iotbay.model.payment;

import au.edu.uts.isd.iotbay.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPaymentMethod {

    private Integer id;
    private User user;
    private PaymentMethod method;

}
